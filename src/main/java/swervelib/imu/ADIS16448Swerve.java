package swervelib.imu;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * IMU Swerve class for the {@link ADIS16448_IMU} device.
 */
public class ADIS16448Swerve extends SwerveIMU
{

  /**
   * {@link ADIS16448_IMU} device to read the current headings from.
   */
  private final ADIS16448_IMU imu;
  /**
   * Offset for the ADIS16448 yaw reading.
   */
  private       double        yawOffset = 0;

  /**
   * Construct the ADIS16448 imu and reset default configurations. Publish the gyro to the SmartDashboard.
   */
  public ADIS16448Swerve()
  {
    imu = new ADIS16448_IMU();
    factoryDefault();
    SmartDashboard.putData(imu);
  }

  /**
   * Reset IMU to factory default.
   */
  @Override
  public void factoryDefault()
  {
    yawOffset = Math.IEEEremainder(imu.getAngle(), 360);
  }

  /**
   * Clear sticky faults on IMU.
   */
  @Override
  public void clearStickyFaults()
  {
    // Do nothing.
  }

  /**
   * Set the yaw in degrees.
   *
   * @param yaw Yaw angle in degrees.
   */
  @Override
  public void setYaw(double yaw)
  {
    yawOffset = Math.IEEEremainder(yaw, 360) + Math.IEEEremainder(imu.getAngle(), 360);
  }

  /**
   * Fetch the yaw/pitch/roll from the IMU.
   *
   * @param yprArray Array which will be filled with {yaw, pitch, roll} in degrees.
   */
  @Override
  public void getYawPitchRoll(double[] yprArray)
  {
    yprArray[0] = Math.IEEEremainder(imu.getAngle(), 360) - yawOffset;
    yprArray[1] = Math.IEEEremainder(imu.getXComplementaryAngle(), 360);
    yprArray[2] = Math.IEEEremainder(imu.getYComplementaryAngle(), 360);
  }

  /**
   * Get the instantiated IMU object.
   *
   * @return IMU object.
   */
  @Override
  public Object getIMU()
  {
    return imu;
  }
}
