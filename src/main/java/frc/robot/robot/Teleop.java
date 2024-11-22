package frc.robot.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Shooter.ShootSpeedCommand;
import frc.robot.commands.arm.ArmPositonCommand;
import frc.robot.commands.swerve.DriveCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Teleop {

    // Subsystems
    private final Swerve swerve;
    private final Shooter shooter;
    private final Arm arm;

    // Joysticks
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;
    private final XboxController operatorXbox;

    // Create triggers
    private final Trigger zeroGyro;
    private final Trigger revShooter;
    private final Trigger armForward;
    private final Trigger armUp;
    private final Trigger armBack;

    // Create Commands
    private final DriveCommand driveCommand;
    private final Command zeroGyroCommand;
    private Command revShooterCommand;
    private final Command armForwardCommand;
    private final Command armUpCommand;
    private final Command armBackCommand;

    public Teleop(RobotContainer robot) {

        // initialize subsystems
        swerve = robot.getSwerve();
        shooter = robot.getShooter();
        arm = robot.getArm();

        // initialize joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);
        operatorXbox = new XboxController(2);

        // initialize triggers
        zeroGyro = driveStick.button(16);
        revShooter = new JoystickButton(operatorXbox, 8);
        revShooterCommand = new ShootSpeedCommand(shooter, 1);
        armForward = new JoystickButton(operatorXbox, 3);
        armUp = new JoystickButton(operatorXbox, 4);
        armBack = new JoystickButton(operatorXbox, 1);

        // initialize commands
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());
        revShooterCommand = new ShootSpeedCommand(shooter, 1);
        armForwardCommand = new ArmPositonCommand(arm, 15);
        armUpCommand = new ArmPositonCommand(arm, 0);
        armBackCommand = new ArmPositonCommand(arm, -15);

        // Set default Commands
        swerve.setDefaultCommand(driveCommand);

    }

    public void configureBindings() {
        zeroGyro.onTrue(zeroGyroCommand);
        revShooter.whileTrue(revShooterCommand);
        armForward.onTrue(armForwardCommand);
        armUp.onTrue(armUpCommand);
        armBack.onTrue(armBackCommand);

    }

}
