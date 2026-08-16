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
        ExtendoM1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ExtendoM2 = hardwareMap.get(DcMotorEx.class, "Extendo Motor 2");
        ExtendoM2.setDirection(DcMotorSimple.Direction.REVERSE);
        ExtendoM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ExtendoM2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
