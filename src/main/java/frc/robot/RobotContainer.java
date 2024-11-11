// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drivebase.CommandSwerveDrivetrain;
import frc.robot.subsystems.limelight.LimelightHelpers;

public class RobotContainer {

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps;
  private double MaxAngularRate = 1.5 * Math.PI;

  private final CommandJoystick driveStick = new CommandJoystick(0);
  private final CommandJoystick rotationStick = new CommandJoystick(1);

  private final CommandSwerveDrivetrain drivebase = TunerConstants.DriveTrain;

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

  private final Telemetry logger = new Telemetry(MaxSpeed);

  //Limelight Vars
  private String rightLL = "limelight-right";
  private String leftLL = "limelight-left";

  private boolean rightTV;
  private boolean leftTV;

  private double rightTA;
  private double leftTA;

  private StatusSignal<Double> robotYaw;

  public RobotContainer() {

    configureBindings();

  }

  private void configureBindings() 
  {
   drivebase.setDefaultCommand(
    drivebase.applyRequest(() -> drive
    .withVelocityX(driveStick.getY()*MaxSpeed)
    .withVelocityY(driveStick.getX()*MaxSpeed)
    .withRotationalRate(-rotationStick.getX()*MaxAngularRate)
    )); 

    driveStick.button(16).onTrue(drivebase.runOnce(() -> drivebase.seedFieldRelative()));

    drivebase.registerTelemetry(logger::telemeterize);
  }

  public Command getAutonomousCommand() {
    return null;
  }

  public void limelightTelmCorrection() {

    rightTV = LimelightHelpers.getTV(rightLL);
    leftTV = LimelightHelpers.getTV(leftLL);

    if (rightTV && leftTV) {
      rightTA = LimelightHelpers.getTA(rightLL);
      leftTA = LimelightHelpers.getTA(leftLL);

      addLLPose((rightTA>leftTA)?rightLL:leftLL);
    }
    else if (leftTV) {
      addLLPose(leftLL);
    }
    else if (rightTV) {
      addLLPose(rightLL);
    }
    
  }

  private void addLLPose(String limelight)
  {
    robotYaw = drivebase.getPigeon2().getYaw();
    LimelightHelpers.SetRobotOrientation(limelight, robotYaw.getValueAsDouble(), 0, 0, 0, 0, 0);

    LimelightHelpers.PoseEstimate limelightEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(limelight);
    drivebase.addVisionMeasurement(limelightEstimate.pose, limelightEstimate.timestampSeconds);
  }


}
