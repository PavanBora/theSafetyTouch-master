package xyz.himanshu.womensafety;

/**
 * Created by himanshusingh on 03/03/17.
 */

public class phone_number {
    private int _id;
    private String _phonenumber;

    public phone_number() {
    }

    public phone_number(String _phonenumber) {
        this._phonenumber = _phonenumber;
    }

    public void set_phonenumber(String _phonenumber) {
        this._phonenumber = _phonenumber;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public String get_phonenumber() {
        return _phonenumber;
    }
}
