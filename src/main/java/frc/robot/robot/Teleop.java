package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.swerve.DriveCommand;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Teleop {
    
    // All the things for teleop will go here

    // Subsystems
    private final Swerve swerve;
    private final Shooter shooter;

    // Joystick
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    // Triggers
    private final Trigger zeroGyro;
    
    // Create Commands
    private final DriveCommand driveCommand;
    private final Command zeroGyroCommand;


    public Teleop(RobotContainer robot)
    {
        // Initialize Subsystems
        swerve = robot.getSwerve();
        shooter = robot.getShooter();

        // Initialize Joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);

        // Initialize Triggers
        zeroGyro = driveStick.button(16);

        // Create the drive Command
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());

        // Set Default Command
        swerve.setDefaultCommand(driveCommand);
    }

    public void configureBindings()
    {
        // Configuring the Trigger to zero the gyro
        zeroGyro.onTrue(zeroGyroCommand);
    }
}
