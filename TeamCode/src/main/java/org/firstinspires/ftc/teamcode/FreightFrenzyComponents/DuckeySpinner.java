package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class DuckeySpinner extends RobotComponent {
    public DcMotor spinner;
//    public boolean isButtonHeld = false;

//    public static final double moveCount = 0; // TODO: fill with accurate number
    public static final double SPINPOWER = 1; // TODO: fill with proper power

    public void setPowerInAuto(final double power){
        spinner.setPower(power);
    }
    public DuckeySpinner(RobotBase base){
        super(base);
        spinner = base.getMapper().mapMotor("duckeySpinner");
    }

    public void spin(double leftPower, double rightPower){
        if(leftPower > 0){// && !isButtonHeld){
//            isLeftButtonHeld = true;
            spinner.setPower(-leftPower);
        }
        else if(rightPower > 0){//!button){
//            isButtonHeld = false;
            spinner.setPower(rightPower);
        }
        else spinner.setPower(0);
    }

    @Override
    public void stop() { spinner.setPower(0);}
}
