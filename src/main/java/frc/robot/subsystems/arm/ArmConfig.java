package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArmConfig {
 
    // CAB bus ID
    public static int ID = 22;

    // Motor type
    public static MotorType motorType = MotorType.kBrushless;

    // PID Values
    public static double p = 0.1;
    public static double i = 0;
    public static double d = 0;

    // Idle Mode
    public static IdleMode idleMode = IdleMode.kBrake;

    // Min + Max Outputs
    public static double outputMin = -1;
    public static double outputMax = 1;

    // Ramp Rate
    public static double openRampRate = 0.25;
    public static double closedRampRate = 0.25;

    // Inversion
    public static boolean inverted = false;

    // Current limits
    public static int smartCurrentStallLimit = 40;
    public static int smartCurrentFreeLimit = 30;

    // Conversion Factor
    public static double postitionConversionFactor = 1;

    // Soft Limits
    public static float softLimitFwd = 15;
    public static float softLimitRev = -15;

    // Control Type
    public static ControlType controlType = ControlType.kPosition;

}
