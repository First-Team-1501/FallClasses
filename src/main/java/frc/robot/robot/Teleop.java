package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.DriveCommand;
import frc.robot.subsystems.drivebase.Swerve;

public class Teleop {

    private final Swerve swerve;

    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    public final DriveCommand driveCommand;
    private final Command zeroGyroCommand;

    private final Trigger zeroGyro;

    public Teleop(RobotContainer robot) {
    

        swerve = robot.getSwerve();

        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);

        //Initalize Triger
        zeroGyro = driveStick.button(16);

        //Initilize Command
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());

        //Set Dafault Command
        swerve.setDefaultCommand(driveCommand);

    }

    public void configureBindings() 
    {
        zeroGyro.onTrue(zeroGyroCommand);
    }
    
}
