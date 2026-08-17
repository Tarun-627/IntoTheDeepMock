package org.firstinspires.ftc.teamcode.Subsystems;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Extendo_Slides {

    public enum State {RETRACT, EXTEND, TRANSFER};

    private final DcMotorEx ExtendoM; // extendo motor 1
    private State state = State.RETRACT;
    public Extendo_Slides(HardwareMap hardwareMap){
        ExtendoM = hardwareMap.get(DcMotorEx.class, "Extendo Motor 1");
        ExtendoM.setDirection(DcMotorSimple.Direction.REVERSE);
        ExtendoM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ExtendoM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExtendoM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case RETRACT:
                ExtendoM.setPower(-1.0);
                ExtendoM.setTargetPosition(0);
                break;
            case EXTEND:
                ExtendoM.setPower(1.0);
                ExtendoM.setTargetPosition(1000); // assumed full extension amount for horizontal slides
                break;
            case TRANSFER:
                ExtendoM.setPower(-1.0);
                ExtendoM.setTargetPosition(100); // assumed full extension amount for horizontal slides
                break;
        }
    }
    public Command retract(){
        return instant(() -> setState(State.RETRACT)).requiring(this);
    }
    public Command extend(){
        return instant(() -> setState(State.EXTEND)).requiring(this);
    }
    public Command transfer(){
        return instant(() -> setState(State.TRANSFER)).requiring(this);
    }
}
