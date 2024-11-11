package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.drivebase.generated.TunerConstants;
import frc.robot.subsystems.shooter.Shooter;

public class RobotContainer {

    // Subsystems
    private final Swerve swerve;
    private final Arm arm;
    private final Shooter shooter;

    // Auto and Teleop controllers
    private final Auto auto;
    private final Teleop teleop;

    public RobotContainer() {
        
        //Initialize Subsystems
        swerve = TunerConstants.DriveTrain;
        arm = new Arm();
        shooter = new Shooter();

        // Initialize Auto and Teleop classes
        auto = new Auto(this);
        teleop = new Teleop(this);
        teleop.configureBindings();
    }

    /**
     * Returns the command to run in autonomous mode.
     *
     * @return Autonomous Command.
     */
    public Command getAutonomousCommand() {
        return auto.getAutonomousCommand();
    }

    // Getters for subsystems
    public Swerve getSwerve() {
        return swerve;
    }

    public Arm getArm() {
        return arm;
    }

    public Shooter getShooter() {
        return shooter;
    }
}
