package homes.hieuiot.ex4_addsubmuldiv_onclick;

public class Calculate {
    double a, b;
    protected Calculate(double a, double b) {
        this.a = a; this.b = b;
    }
    protected double Plus() {
        return a+b;
    }
    protected double Minus() {
        return a-b;
    }
    protected double Multiply() {
        return a*b;
    }
    protected double Divide() {
        return a/b;
    }
}
