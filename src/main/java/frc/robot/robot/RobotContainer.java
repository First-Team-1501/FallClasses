package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.drivebase.generated.TunerConstants;

public class RobotContainer {

    // Subsystems
    private final Swerve swerve;

    //teleop controller
    private final Teleop teleop;


    public RobotContainer() {
        
        //Initialize Subsystems
        swerve = TunerConstants.DriveTrain;

        //init controllers
        teleop = new Teleop(this);
        teleop.configureBindings();

    }

    /**
     * Returns the command to run in autonomous mode.
     *
     * @return Autonomous Command.
     */
    public Command getAutonomousCommand() {
        return null;
    }

    // Getters for subsystems
    public Swerve getSwerve() {
        return swerve;
    }

}
