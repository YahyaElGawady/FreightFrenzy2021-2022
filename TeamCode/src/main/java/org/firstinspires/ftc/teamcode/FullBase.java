package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.*;

public class FullBase extends RobotBase {
    public Drivetrain drivetrain;
    public Sucker sucker;
    public OuttakeBucket outtakeBucket;
    public DuckeySpinner duckeySpinner;
    public DuckDetector duckDetector;

    private RobotComponent[] components = new RobotComponent[4];

    public double rpm = 0;
    public static final double inchesToWobble = 18.5;
    public static final double unsafeInchesToDuckySpinner = 33;
    public static final double safeInchesToDuckySpinner   = -8;
    public static final double inchesToDuckyParking = 14;
    public static final double inchesToDuckyParking2 = 3;

    public FullBase(Telemetry telemetry, LinearOpMode opMode, HardwareMap hardwaremap, boolean debugging) {
        super(telemetry, opMode, hardwaremap,debugging);

    }

    @Override
    public void init() {
        //create drivetrain
        telemetry.addLine("Drivetrain about to init");
        telemetry.update();
        drivetrain = new Drivetrain(this);
        telemetry.addLine("drive inited");
        telemetry.update();
        components[0] = drivetrain;
        telemetry.addLine("Sucker about to init");
        telemetry.update();
        sucker = new Sucker(this);
        telemetry.addLine("sucker inited");
        telemetry.update();
        components[1] = sucker;

        telemetry.addLine("Outtake about to init");
        telemetry.update();
        outtakeBucket = new OuttakeBucket(this);
        telemetry.addLine("outtake inited");
        telemetry.update();
        components[2] = outtakeBucket;

        telemetry.addLine("DuckeySpinner about to init");
        telemetry.update();
        duckeySpinner = new DuckeySpinner(this);
        telemetry.addLine("DuckeySpinner inited");
        telemetry.update();
        components[3] = duckeySpinner;
//        telemetry.addLine("Sucker about to init");
//        sucker = new Sucker(this);
//        telemetry.addLine("sucker inited");
//        components[1] = sucker;
//
//        telemetry.addLine("Outtake about to init");
//        outtakeBucket = new OuttakeBucket(this);
//        telemetry.addLine("outtake inited");
//        components[2] = outtakeBucket;
        //initialize DuckDetector
        telemetry.addLine("DuckDetector about to init");
        telemetry.update();
        duckDetector = new DuckDetector(opMode);
        telemetry.addLine("DuckDetector inited");
        telemetry.update();

        outtakeBucket.dump(true);

    }

    /**
     * @param
     * */
    public void dumpFromDuckPos(int redDucky){
        duckDetector.takePicture();
        int spos = FullBase.duckLocationToSliderPosition(duckDetector.mostDuckyArea());
        if(spos == -1){
            outtakeBucket.slide(OuttakeBucket.TOP);
        }
        else outtakeBucket.slide(spos);
        drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 45*redDucky);
        drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
                inchesToWobble, inchesToWobble,
                inchesToWobble, inchesToWobble);

        outtakeBucket.dump(true);
        try{ Thread.sleep(500); } catch (Exception e) {}
        outtakeBucket.dump(true);
    }
    public void safeDuckeySpinnerSideAuto(int red){
        drivetrain.gyroDrive(
                Drivetrain.DRIVE_SPEED, safeInchesToDuckySpinner,
                safeInchesToDuckySpinner, safeInchesToDuckySpinner,
                safeInchesToDuckySpinner, 0, 0);
        drivetrain.gyroTurn(Drivetrain.TURN_SPEED,( (red == 1) ? 60 : 20));
//        drivetrain.moveInches(
//                Drivetrain.DRIVE_SPEED, safeInchesToDuckySpinner,
//                safeInchesToDuckySpinner, safeInchesToDuckySpinner,
//                safeInchesToDuckySpinner);

        duckeySpinner.spinner.setPower(-1 * red);
        try{ Thread.sleep(3000); } catch (Exception e) {}
        duckeySpinner.spin(false);

        drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 90*red);
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1,
                 inchesToDuckyParking + ((red == 1) ? 0 : 0), inchesToDuckyParking + ((red == 1) ? 0 : 0),
                inchesToDuckyParking + ((red == 1) ? 0 : 0), inchesToDuckyParking + ((red == 1) ? 0 : 0), 110 * red, 0);
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                inchesToDuckyParking2, inchesToDuckyParking2,
                inchesToDuckyParking2, inchesToDuckyParking2, 80 * red, 0);
        drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 90*red);
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                1.25, 1.25,
                1.25, 1.25, 90 * red, 0);

        if(red == -1)
            drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                    -4, -4,
                    -4, -4, 90 * red, 0);
    }
    public void duckeySpinnerSideAuto(int red){
        dumpFromDuckPos(red);

        drivetrain.gyroTurn(Drivetrain.TURN_SPEED,-155*red);
        drivetrain.moveInches(
                Drivetrain.DRIVE_SPEED, unsafeInchesToDuckySpinner,
                unsafeInchesToDuckySpinner, unsafeInchesToDuckySpinner, unsafeInchesToDuckySpinner);

        duckeySpinner.spin(true);
        try{ Thread.sleep(3000); } catch (Exception e) {}
        duckeySpinner.spin(false);

        drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 100*red);
        drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
                inchesToDuckyParking, inchesToDuckyParking,
                inchesToDuckyParking, inchesToDuckyParking);
    }
    public void warehouseSideAuto(int red){
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, 11, 11, 11, 11 , 0 , 0);
        drivetrain.gyroTurn(Drivetrain.DRIVE_SPEED, -90 * red);
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, 30, 30, 30, 30, -90 * red, 0);
    }
    public void warehouseSideAutoBlue(){
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, 11, 11, 11, 11, 0 , 0);
        drivetrain.gyroTurn(Drivetrain.DRIVE_SPEED, 80 );
        drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, 30, 30, 30, 30, 90, 0);
    }
    public void wait(double timeInMs){

        while(this.opMode.time < (timeInMs/1000));
    }
    public static int duckLocationToSliderPosition(DuckDetector.DuckLocation loc){
        switch(loc) {
            case LEFT:
                return -1;
            case MIDDLE:
                return OuttakeBucket.BOTTOM;
            default:
                return OuttakeBucket.TOP;
        }
    }
    @Override
    public void stop() {
        for( int i = 0; i < components.length; ++i){
            components[i].stop();
        }
    }
}
