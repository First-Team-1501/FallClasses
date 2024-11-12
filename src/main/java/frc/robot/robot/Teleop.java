package frc.robot.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.arm.ArmPositionCommand;
import frc.robot.commands.drivebase.DriveCommand;
import frc.robot.commands.shooter.ShooterSpeedCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Teleop {

    private final Swerve swerve;
    private final Arm arm;
    private final Shooter shooter;

    // Joysticks
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    // Operator Xbox Controller
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
    private Trigger armToZero;

    // Commands
    private final DriveCommand driveCommand;
    private final Command zeroGyroCommand;
    private final Command revShooterCommand;
    private final Command armToZeroCommand;

    public Teleop(RobotContainer robot) {
        this.swerve = robot.getSwerve();
        this.arm = robot.getArm();
        this.shooter = robot.getShooter();

        // Initialize joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);
        operatorXbox = new XboxController(2);

        // Initialize triggers
        zeroGyro = driveStick.button(16);
        revShooter = new JoystickButton(operatorXbox, ControllerButton.RightTrigger.value);
        armToZero = new JoystickButton(operatorXbox, ControllerButton.A.value);

        // Initialize commands
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());
        revShooterCommand = new ShooterSpeedCommand(shooter, 0, true);
        armToZeroCommand = new ArmPositionCommand(arm, 0);

        // Set default command
        swerve.setDefaultCommand(driveCommand);

    }

    public void configureBindings() {

        zeroGyro.onTrue(zeroGyroCommand);
        revShooter.whileTrue(revShooterCommand);
        armToZero.onTrue(armToZeroCommand);

    }

}
