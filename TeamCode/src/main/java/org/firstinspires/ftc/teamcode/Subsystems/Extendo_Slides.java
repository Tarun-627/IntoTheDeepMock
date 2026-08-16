package org.firstinspires.ftc.teamcode.Subsystems;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Extendo_Slides {

    public enum State {RETRACT, EXTEND};

    private final DcMotorEx ExtendoM1; // extendo motor 1

    private final DcMotorEx ExtendoM2; // extendo motor 2
    private State state = State.RETRACT;
    public Extendo_Slides(HardwareMap hardwareMap){
        ExtendoM1 = hardwareMap.get(DcMotorEx.class, "Extendo Motor 1");
        ExtendoM1.setDirection(DcMotorSimple.Direction.REVERSE);
        ExtendoM1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ExtendoM1.setTargetPosition(1000); // assumed full extension amount for horizontal slides
        ExtendoM1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExtendoM1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ExtendoM2 = hardwareMap.get(DcMotorEx.class, "Extendo Motor 2");
        ExtendoM2.setDirection(DcMotorSimple.Direction.REVERSE);
        ExtendoM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ExtendoM2.setTargetPosition(1000);
        ExtendoM2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExtendoM2.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case RETRACT:
                ExtendoM1.setPower(-1.0);
                ExtendoM2.setPower(-1.0);
                break;
            case EXTEND:
                ExtendoM1.setPower(1.0);
                ExtendoM2.setPower(1.0);
                break;
        }
    }
    public Command retract(){
        return instant(() -> setState(State.RETRACT)).requiring(this);
    }
    public Command extend(){
        return instant(() -> setState(State.EXTEND)).requiring(this);
    }
}
