// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.drivebase.Swerve;

public class DriveCommand extends Command {
  
  private final Swerve swerve;
  private final CommandJoystick driveStick;
  private final CommandJoystick rotationStick;
  
  /** Creates a new DriveCommand. */
  public DriveCommand(Swerve swerve, CommandJoystick driveStick, CommandJoystick rotationStick) {
    
    this.swerve = swerve;
    this.driveStick = driveStick;
    this.rotationStick = rotationStick;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    SwerveRequest.FieldCentric driveRequest = swerve.driveRequest
      .withVelocityX(driveStick.getY() * swerve.maxSpeed)
      .withVelocityY(driveStick.getX() * swerve.maxSpeed)
      .withRotationalRate(-rotationStick.getX() * swerve.maxAngularRate);

    swerve.setControl(driveRequest);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
