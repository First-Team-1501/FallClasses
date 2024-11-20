package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.DriveCommand;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Teleop {

    // Subsytems
    private final Swerve swerve;
    private final Shooter shooter;

    // Joysticks
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    // Create Commands
    public final DriveCommand driveCommand;
    private final Command zeroGyroCommand;

    // Zero Gyro
    private final Trigger zeroGyro;

    public Teleop(RobotContainer robot) {
    

        // Initilize Subsytems
        swerve = robot.getSwerve();
        shooter = robot.getShooter();

        // Initilize Joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);

        // Initalize Triger
        zeroGyro = driveStick.button(16);

        // Initilize Command
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());

        // Set Dafault Command
        swerve.setDefaultCommand(driveCommand);

    }

    public void configureBindings() 
    {
        zeroGyro.onTrue(zeroGyroCommand);
    }
    
}
