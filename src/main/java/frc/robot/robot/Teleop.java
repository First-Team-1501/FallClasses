package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.swerve.DriveCommand;
import frc.robot.subsystems.drivebase.Swerve;

public class Teleop {

    private final Swerve swerve;

    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    private final Trigger zeroGyro;

    // Create Commands
    private final DriveCommand driveCommand;
    private final Command zeroGyroCommand;

    public Teleop(RobotContainer robot)
    {
        swerve = robot.getSwerve();

        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);

        zeroGyro = driveStick.button(16);

        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);

        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());

        swerve.setDefaultCommand(driveCommand);


        
    }
    

    public void configureBindings()
    {
        zeroGyro.onTrue(zeroGyroCommand);
    }
    
}
