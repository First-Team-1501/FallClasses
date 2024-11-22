package frc.robot.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.arm.ArmPositionCommand;
import frc.robot.commands.shooter.ShooterSpeedCommand;
import frc.robot.commands.swerve.DriveCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Teleop {
    
    // All the things for teleop will go here

    // ??? Subsystems ??? //
    private final Swerve swerve;
    private final Shooter shooter;
    private final Arm arm;

    // Joystick
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    // Creating the XBOX Controller
    private final XboxController operatorXbox;

    // Triggers
    private final Trigger zeroGyro;
    private final Trigger revShooter;
    private final Trigger armFwd;
    private final Trigger armUp;
    private final Trigger armBack;
    
    // Create Commands
    private final DriveCommand driveCommand;
    private final Command zeroGyroCommand;
    private final Command revShooterCommand;
    private final Command armFwdCommand;
    private final Command armUpCommand;
    private final Command armBackCommand;


    public Teleop(RobotContainer robot)
    {
        // *** Initialize Subsystems *** //
        swerve = robot.getSwerve();
        shooter = robot.getShooter();
        arm = robot.getArm();

        // Initialize Joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);

        // Initialize XBOX Controller
        operatorXbox = new XboxController(2);

        // Initialize Triggers
        zeroGyro = driveStick.button(16);
        revShooter = new JoystickButton(operatorXbox, 8);
        
        // Button 3 is the B Button
        armFwd = new JoystickButton(operatorXbox, 3);
        // Button 4 is the Y Button
        armUp = new JoystickButton(operatorXbox, 4);
        // Button 1 is the X Button
        armBack = new JoystickButton(operatorXbox, 1);



        // Create the drive Command
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());
        revShooterCommand = new ShooterSpeedCommand(shooter, 1);
        armFwdCommand = new ArmPositionCommand(arm, 15);
        armUpCommand = new ArmPositionCommand(arm, 0);
        armBackCommand = new ArmPositionCommand(arm, -15);

        // Set Default Command
        swerve.setDefaultCommand(driveCommand);
    }

    public void configureBindings()
    {
        // Configuring the Trigger to zero the gyro
        zeroGyro.onTrue(zeroGyroCommand);

        revShooter.whileTrue(revShooterCommand);
        armFwd.onTrue(armFwdCommand);
        armUp.onTrue(armUpCommand);
        armBack.onTrue(armBackCommand);
    }
}
