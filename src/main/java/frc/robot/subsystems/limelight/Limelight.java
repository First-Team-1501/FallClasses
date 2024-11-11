

package frc.robot.subsystems.limelight;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.GenericEntry;
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import java.util.Optional;

public class Limelight extends SubsystemBase {

    // * This is for the Limelight going to Shuffleboard.
    GenericEntry limelightTX;
    GenericEntry limelightTY;
    GenericEntry limelightTA;
    GenericEntry limelightPipelineIndex;
    GenericEntry limelightTV;
    GenericEntry limelightCL;


  /** Creates a new Limelight. */
  public Limelight() {
    setupLimelight();
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateShuffleboardLimelight();
  }

  private void setupLimelight() {

    //setPipelineUsingAllianceColor();

    LimelightHelpers.getLimelightNTTable(null);
    LimelightHelpers.getLimelightNTTableEntry(null, "tid");


    LimelightHelpers.setLEDMode_PipelineControl("limelight-right");

    shuffleboardInit();
    //shuffleboardintakeInit();
    

  }

  public void shuffleboardInit() {
    // This adds the Limelight tx value to Shuffleboard
    limelightTX = Shuffleboard.getTab("light-right")
      .add("Limelight TX", LimelightHelpers.getTX("limelight-right"))
      .getEntry();

    // This adds the Limelight ty value to Shuffleboard
    limelightTY = Shuffleboard.getTab("Limelight")
      .add("Limelight TY", LimelightHelpers.getTY("limelight-right"))
      .getEntry();

    // This adds the Limelight ta value to Shuffleboard
    limelightTA = Shuffleboard.getTab("Limelight")
      .add("Limelight TA", LimelightHelpers.getTA("limelight-right"))
      .getEntry();

    // This adds the Limelight pipeline index value to Shuffleboard
    limelightPipelineIndex = Shuffleboard.getTab("Limelight")
      .add("Limelight Pipeline Index", LimelightHelpers.getCurrentPipelineIndex("limelight-right"))
      .getEntry();

    // This adds the Limelight tv value to Shuffleboard
    limelightTV = Shuffleboard.getTab("Limelight")
      .add("Limelight-Right Target", LimelightHelpers.getTV("limelight-right"))
      .withWidget("Boolean Box")
      .getEntry();

    // This adds the Limelight cl value to Shuffleboard
    limelightCL = Shuffleboard.getTab("Limelight")
      .add("Limelight CL", LimelightHelpers.getLimelightNTDouble("limelight-right", "cl"))
      .getEntry();
    
    // This adds the Limelight tv value to Shuffleboard
    limelightTV = Shuffleboard.getTab("Limelight")
      .add("Limelight-Right Target", LimelightHelpers.getTV("limelight-left"))
      .withWidget("Boolean Box")
      .getEntry();
    
  }

  public double tX() {
    return limelightTX.getDouble(0.0);
  }
  public double tY() {
    return limelightTY.getDouble(0.0);
  }
  public double tA() {
    return limelightTA.getDouble(0.0);
  }
  public boolean hasTarget() {
    return limelightTV.getBoolean(false);
  }

  public boolean isLocked()
  {
    return (tX() < 0.5 && tX() > -0.5) && (tY() < 0.5 && tY() > -0.5);
  }

  // * This is for the Limelight going to Shuffleboard.
  public void updateShuffleboardLimelight()  {
    limelightTX.setDouble(LimelightHelpers.getTX("limelight-right"));
    limelightTY.setDouble(LimelightHelpers.getTY("limelight-right"));
    limelightTA.setDouble(LimelightHelpers.getTA("limelight-right"));
    limelightPipelineIndex.setDouble(LimelightHelpers.getCurrentPipelineIndex("limelight-right"));
    limelightTV.setBoolean(LimelightHelpers.getTV("limelight-right"));
    limelightTV.setBoolean(LimelightHelpers.getTV("limelight-left"));
    limelightCL.setDouble(LimelightHelpers.getLimelightNTDouble("limelight-right", "cl"));
  }
}

