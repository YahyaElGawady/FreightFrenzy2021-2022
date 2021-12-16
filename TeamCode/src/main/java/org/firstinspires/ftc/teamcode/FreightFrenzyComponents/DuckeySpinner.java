package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class DuckeySpinner extends RobotComponent {
    public CRServo spinner;
    public boolean isLeftButtonHeld = false;
    public boolean isRightButtonHeld = false;

//    public static final double moveCount = 0; // TODO: fill with accurate number
    public static final double SPINPOWER = 1; // TODO: fill with proper power

    public DuckeySpinner(RobotBase base){
        super(base);
        spinner = base.getMapper().mapCRServo("duckeySpinner");
    }

    public void spin(boolean right, boolean left){
        if(right && !isRightButtonHeld){
            isRightButtonHeld = true;
            spinner.setPower(SPINPOWER);
        }
        else if(left && !isLeftButtonHeld){
            isLeftButtonHeld = true;
            spinner.setPower(-SPINPOWER);
        }
        else if(!(right || left)){
            isLeftButtonHeld = isRightButtonHeld = false;
            spinner.setPower(0);
        }
    }

    @Override
    public void stop() { spinner.setPower(0);}
}
