package org.firstinspires.ftc.teamcode.Subsystems.Intake;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    public enum State {INTAKE, IDLE};

    private final DcMotorEx intake;
    private State state = State.IDLE;
    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotorEx.class, "Extendo Intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case INTAKE:
                intake.setPower(-1.0);
                break;
            case IDLE:
                intake.setPower(0);
                break;
        }
    }

    //intake/outake/idle
    public Command intake(){
        return instant(() -> setState(State.INTAKE)).requiring(this);
    }
    public Command idle(){
        return instant(() -> setState(State.IDLE)).requiring(this);
    }
}
