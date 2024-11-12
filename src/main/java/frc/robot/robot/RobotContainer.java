package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.drivebase.generated.TunerConstants;

public class RobotContainer {

    // Subsystems
    private final Swerve swerve;

    public RobotContainer() {
        
        //Initialize Subsystems
        swerve = TunerConstants.DriveTrain;

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
