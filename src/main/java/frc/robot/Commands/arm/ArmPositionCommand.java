// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;

public class ArmPositionCommand extends Command {

private Arm arm;
private double position;
  /** Creates a new ArmPositionCommand. */
  public ArmPositionCommand(Arm arm, double position) 
  {
    this.arm = arm;
    this.position = position;
    
    addRequirements(arm);
  }

 
  @Override
  public void initialize() 
  {
    arm.set(position);
  }

  public void execute() 
  {

  }

  public void end(boolean interrupted) {}


  public boolean isFinished() {
    return false;
  }
}
