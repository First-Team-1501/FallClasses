package frc.robot.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.DriveCommand;
import frc.robot.Commands.arm.ArmPositionCommand;
import frc.robot.Commands.shooter.ShooterSpeedCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Teleop {

    // Subsytems
    private final Swerve swerve;
    private final Shooter shooter;
    private final Arm arm;

    // Joysticks
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;
    private final XboxController operatorXbox;

    // Creat triggers
    private final Trigger zeroGyro;
    private final Trigger revShooter;
    private final Trigger armForward;
    private final Trigger armUp;
    private final Trigger armBack;
    
    // Create commands
    public final DriveCommand driveCommand;
    private final Command zeroGyroCommand;
    private final Command revShooterCommand;
    private final Command armForwardCommand;
    private final Command armUpCommand;
    private final Command armBackCommand;
   
   
    public Teleop(RobotContainer robot) {
    

        // Initilize subsytems
        swerve = robot.getSwerve();
        shooter = robot.getShooter();
        arm = robot.getArm();

        // Initilize joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);
        operatorXbox = new XboxController(2);


        // Initalize trigers
        zeroGyro = driveStick.button(16);
        revShooter = new JoystickButton(operatorXbox, 8);
        armForward = new JoystickButton(operatorXbox, 3);
        armUp = new JoystickButton(operatorXbox, 4);
        armBack = new JoystickButton(operatorXbox, 1);


        // Initilize commands
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());
        revShooterCommand = new ShooterSpeedCommand(shooter, 1);
        armForwardCommand = new ArmPositionCommand(arm, 15);
        armUpCommand = new ArmPositionCommand(arm, 0);
        armBackCommand = new ArmPositionCommand(arm, -15);

        // Set default command
        swerve.setDefaultCommand(driveCommand);

    }

    public void configureBindings() 
    {
        zeroGyro.onTrue(zeroGyroCommand);
        revShooter.whileTrue(revShooterCommand);
        armForward.onTrue(armForwardCommand);
        armUp.onTrue(armUpCommand);
        armBack.onTrue(armBackCommand);
    }
    
}
