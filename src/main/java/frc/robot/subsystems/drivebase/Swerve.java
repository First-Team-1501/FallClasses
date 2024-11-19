package frc.robot.subsystems.drivebase;

import java.util.function.Supplier;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.drivebase.generated.TunerConstants;
import frc.robot.subsystems.limelight.LimelightHelpers;

/**
 * Class that extends the Phoenix SwerveDrivetrain class and implements
 * Subsystem so it can be used in command-based projects easily.
 */
public class Swerve extends SwerveDrivetrain implements Subsystem {

    /* Blue alliance sees forward as 0 degrees (toward red alliance wall) */
    private final Rotation2d BlueAlliancePerspectiveRotation = Rotation2d.fromDegrees(0);
    /* Red alliance sees forward as 180 degrees (toward blue alliance wall) */
    private final Rotation2d RedAlliancePerspectiveRotation = Rotation2d.fromDegrees(180);
    /* Keep track if we've ever applied the operator perspective before or not */
    private boolean hasAppliedOperatorPerspective = false;

    // Drive Parameters
    public final double maxSpeed;
    public final double maxAngularRate;

    // Drive Request
    public final SwerveRequest.FieldCentric driveRequest;

    // Ross was here
    // Lol

    // Telemetry
    private final Telemetry logger;

    // Auto
    private final SwerveRequest.ApplyChassisSpeeds AutoRequest = new SwerveRequest.ApplyChassisSpeeds();

    /**
     * Constructs a new Swerve subsystem without odometry update frequency.
     *
     * @param driveTrainConstants SwerveDrivetrainConstants instance.
     * @param modules             SwerveModuleConstants instances.
     */
    public Swerve(SwerveDrivetrainConstants driveTrainConstants, SwerveModuleConstants... modules) {
        super(driveTrainConstants, modules);

        // Initialize drive parameters
        this.maxSpeed = TunerConstants.kSpeedAt12VoltsMps;
        this.maxAngularRate = 1.5 * Math.PI;

        // Initialize Drive Request
        this.driveRequest = new SwerveRequest.FieldCentric()
                .withDeadband(maxSpeed * 0.1)
                .withRotationalDeadband(maxAngularRate * 0.1)
                .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

        // Initialize Telemetry
        this.logger = new Telemetry(TunerConstants.kSpeedAt12VoltsMps);

        // Register Telemetry with Swerve Subsystem
        this.registerTelemetry(this.logger::telemeterize);

        // Configure PathPlanner
        configurePathPlanner();
    }

    /**
     * Applies a custom SwerveRequest supplied by the caller.
     *
     * @param requestSupplier A Supplier that provides a SwerveRequest.
     * @return A Command that applies the provided SwerveRequest.
     */
    public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
        return run(() -> this.setControl(requestSupplier.get()));
    }

    // Limelight
    private final String rightLL = "limelight-right";
    private final String leftLL = "limelight-left";

    private boolean rightTV;
    private boolean leftTV;

    private double rightTA;
    private double leftTA;

    private StatusSignal<Double> robotYaw;

    /**
     * Handles Limelight telemetry correction.
     */
    private void limelightTelmCorrection() {

        rightTV = LimelightHelpers.getTV(rightLL);
        leftTV = LimelightHelpers.getTV(leftLL);

        if (rightTV && leftTV) {
            rightTA = LimelightHelpers.getTA(rightLL);
            leftTA = LimelightHelpers.getTA(leftLL);

            addLLPose((rightTA > leftTA) ? rightLL : leftLL);
        } else if (leftTV) {
            addLLPose(leftLL);
        } else if (rightTV) {
            addLLPose(rightLL);
        }
    }

    /**
     * Adds Limelight pose measurement.
     *
     * @param limelight The name of the Limelight camera.
     */
    private void addLLPose(String limelight) {
        robotYaw = this.getPigeon2().getYaw();
        LimelightHelpers.SetRobotOrientation(limelight, robotYaw.getValue(), 0, 0, 0, 0, 0);

        LimelightHelpers.PoseEstimate limelightEstimate = LimelightHelpers
                .getBotPoseEstimate_wpiBlue_MegaTag2(limelight);
        this.addVisionMeasurement(limelightEstimate.pose, limelightEstimate.timestampSeconds);
    }

    @Override
    public void periodic() {

        // Periodically try to apply the operator perspective
        if (!hasAppliedOperatorPerspective || DriverStation.isDisabled()) {
            DriverStation.getAlliance().ifPresent((allianceColor) -> {
                this.setOperatorPerspectiveForward(
                        allianceColor == Alliance.Red ? RedAlliancePerspectiveRotation
                                : BlueAlliancePerspectiveRotation);
                hasAppliedOperatorPerspective = true;
            });
        }
        limelightTelmCorrection();

    }

  
    /**
     * Configures the PathPlanner AutoBuilder for autonomous path following.
     */
    public void configurePathPlanner() {
        double driveBaseRadius = 0.408601672; // distance from center of robot to center of farthest wheel in meters
        for (var moduleLocation : m_moduleLocations) {
            driveBaseRadius = Math.max(driveBaseRadius, moduleLocation.getNorm());
        }

        AutoBuilder.configureHolonomic(
                () -> this.getState().Pose, // Supplier of current robot pose
                this::seedFieldRelative, // Consumer for seeding pose against auto
                this::getCurrentRobotChassisSpeeds,
                (speeds) -> this.setControl(AutoRequest.withSpeeds(speeds)), // Consumer of ChassisSpeeds to drive the
                                                                             // robot
                new HolonomicPathFollowerConfig(
                        new PIDConstants(10, 0, 0.2), // Translation PID
                        new PIDConstants(3, 0, 0),     // Rotation PID
                        TunerConstants.kSpeedAt12VoltsMps,
                        driveBaseRadius,
                        new ReplanningConfig()),
                () -> DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red, // Flipping condition based on
                                                                                         // alliance
                this // Subsystem for requirements
        );
    }

    /**
     * Retrieves the current chassis speeds of the robot.
     *
     * @return Current ChassisSpeeds.
     */
    public ChassisSpeeds getCurrentRobotChassisSpeeds() {
        return m_kinematics.toChassisSpeeds(getState().ModuleStates);
    }

    /**
     * Generates an autonomous command based on the given auto name using
     * PathPlanner.
     *
     * @param autoName The name of the autonomous path.
     * @return A Command that executes the autonomous path.
     */
    public Command getAutoCommand(String autoName) {
        Pose2d startingPose = PathPlannerAuto.getStaringPoseFromAutoFile(autoName);
        this.seedFieldRelative(startingPose);

        return new PathPlannerAuto(autoName);
    }

    /**
     * Generates an autonomous PathPlanner command for the specified path.
     *
     * @param pathName The name of the path.
     * @return A Command that executes the specified path.
     */
    public Command getAutoPath(String pathName) {
        return new PathPlannerAuto(pathName);
    }
}
