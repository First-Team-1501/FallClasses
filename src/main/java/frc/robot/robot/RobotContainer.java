package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.drivebase.generated.TunerConstants;
import frc.robot.subsystems.shooter.Shooter;

public class RobotContainer {

    // Subsystems
    private final Swerve swerve;
    private final Shooter shooter;
private final Arm arm;


    private final Teleop teleop;
    private final Auto auto;

    public RobotContainer() {

        // Initialize Subsystems
        swerve = TunerConstants.DriveTrain;
        shooter = new Shooter();
        arm = new Arm();



// initialize controllers
        teleop = new Teleop(this);
    teleop.configureBindings();
    auto = new Auto(this);


    }

    /**
     * Returns the command to run in autonomous mode.
     *
     * @return Autonomous Command.
     */
    public Command getAutonomousCommand() {
        return auto.getAutoCommand();
    }

    // Getters for subsystems
    public Swerve getSwerve() {
        return swerve;

    }

public Shooter getShooter(){
    return shooter;
}

public Arm getArm(){
    return arm;
}

}
