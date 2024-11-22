package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArmConfig {
    
    // **** ARM CONFIG **** //

    // CANbus IDs
    public static int ID = 22;

    // Motor Type
    public static MotorType motorType = MotorType.kBrushless;

    // PID Values
    public static double p = 0.1;
    public static double i = 0.0;
    public static double d = 0.0;

    // Idle Mode
    public static IdleMode idleMode = IdleMode.kBrake;

    // Min + Max Output
    public static double outputMin = -1.0;
    public static double outputMax = 1.0;

    // Ramp Rate
    public static double openRampRate = 0; // Time in seconds (1 is good for percent based stuff) (percent mode)
    public static double closedRampRate = 0; // IF IN POSITION MODE, THIS IS THE RAMP RATE SETTING!!!

    // Inverted
    public static boolean kInverted = true;

    // Current Limits
    public static int smartCurrentStallLimit = 40; // Amps
    public static int smartCurrentFreeLimit = 30; // Amps

    // Conversion Factor
    public static double positionConversionFactor = 1.0; // 1.0 is default
    public static double velocityConversionFactor = 1.0; // 1.0 is default

    // Soft Limits
    public static float softLimitFwd = 15;
    public static float softLimitRev = -15;

    // Control Type
    public static ControlType controlType = ControlType.kPosition;
}
