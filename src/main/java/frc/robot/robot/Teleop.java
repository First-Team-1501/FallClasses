package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.swerve.DriveCommand;
import frc.robot.subsystems.drivebase.Swerve;

public class Teleop {
    
    // All the things for teleop will go here

    // Subsystems
    private final Swerve swerve;
    private final Shooter shooter;

    // Joysticks
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    // Xbox Controller
    XboxController operatorXbox;

    private enum ControllerButton {
        A(2),
        B(3),
        X(1),
        Y(4),

        LeftTrigger(7),
        RightTrigger(8),

        LeftBumper(5),
        RightBumper(6),

        Select(10),
        Back(9);

        public final int value;

        ControllerButton(int val) {
                value = val;
        }
    }

    // Triggers
    private Trigger zeroGyro;
    private Trigger revShooter;
    private Trigger revShooter2;
    
    // Create Commands
    private final DriveCommand driveCommand;
    private final Command zeroGyroCommand;
    private final Command revShooterCommand;

    public Teleop(RobotContainer robot)
    {
        this.swerve = robot.getSwerve();
        this.shooter = robot.getShooter();
        
        // Initialize joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);
        operatorXbox = new XboxController(2);

        // Initialize triggers
        zeroGyro = driveStick.button(16);
        revShooter2 = driveStick.button(17);
        revShooter = new JoystickButton(operatorXbox, ControllerButton.RightTrigger.value);
        
        // Initialize commands
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());
        revShooterCommand = new ShooterSpeedCommand(shooter, 1, true);

        // Set default command
        swerve.setDefaultCommand(driveCommand);

    }

    public void configureBindings()
    {
        // Driver Controls
        zeroGyro.onTrue(zeroGyroCommand);
        revShooter2.whileTrue(revShooterCommand);

        // Operator Controls
        revShooter.whileTrue(revShooterCommand);
    }
}
