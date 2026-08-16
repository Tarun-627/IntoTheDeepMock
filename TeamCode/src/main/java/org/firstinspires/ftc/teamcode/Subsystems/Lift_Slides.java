package org.firstinspires.ftc.teamcode.Subsystems;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift_Slides {

    public enum State {BASE, LOWBOX, HIGHBOX, SPECIMEN}; // different heights lift slides need to reach

    private final DcMotorEx LiftM1; // lift motor 1

    private final DcMotorEx LiftM2; // lift motor 2
    private State state = State.BASE;

    // assumed positions
    private final int BASE_POS = 0;
    private final int LOWBOX_POS = 1500;
    private final int HIGHBOX_POS = 3000;
    private final int SPECIMEN_POS = 900;
    public Lift_Slides(HardwareMap hardwareMap){
        LiftM1 = hardwareMap.get(DcMotorEx.class, "Lift Motor 1");
        LiftM1.setDirection(DcMotorSimple.Direction.REVERSE);
        LiftM1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftM1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftM1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        LiftM2 = hardwareMap.get(DcMotorEx.class, "Lift Motor 2");
        LiftM2.setDirection(DcMotorSimple.Direction.REVERSE);
        LiftM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftM2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftM2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case BASE:
                // motor 1
                LiftM1.setTargetPosition(BASE_POS);
                LiftM1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM1.setPower(1.0);
                // motor 2
                LiftM2.setTargetPosition(BASE_POS);
                LiftM2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM2.setPower(1.0);
                break;
            case LOWBOX:
                // motor 1
                LiftM1.setTargetPosition(LOWBOX_POS);
                LiftM1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM1.setPower(1.0);
                // motor 2
                LiftM2.setTargetPosition(LOWBOX_POS);
                LiftM2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM2.setPower(1.0);
                break;
            case HIGHBOX:
                // motor 1
                LiftM1.setTargetPosition(HIGHBOX_POS);
                LiftM1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM1.setPower(1.0);
                // motor 2
                LiftM2.setTargetPosition(HIGHBOX_POS);
                LiftM2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM2.setPower(1.0);
                break;
            case SPECIMEN:
                // motor 1
                LiftM1.setTargetPosition(SPECIMEN_POS);
                LiftM1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM1.setPower(1.0);
                // motor 2
                LiftM2.setTargetPosition(SPECIMEN_POS);
                LiftM2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftM2.setPower(1.0);
                break;
        }
    }
    public Command base(){
        return instant(() -> setState(State.BASE)).requiring(this);
    }
    public Command lowbox(){
        return instant(() -> setState(State.LOWBOX)).requiring(this);
    }
    public Command highbox(){
        return instant(() -> setState(State.HIGHBOX)).requiring(this);
    }
    public Command specimen(){
        return instant(() -> setState(State.SPECIMEN)).requiring(this);
    }


}
