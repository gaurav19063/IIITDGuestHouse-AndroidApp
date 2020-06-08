package in.ac.iiitd.iiitd_guesthouseapp.Model;

import com.google.firebase.auth.FirebaseUser;

/**
 * Project IIITDGuestHouseApp 2
 * Created by Anchit Gupta on 2019-10-04.
 * Under the MIT License
 */

public class UserDetail {

    private FirebaseUser user;

    public UserDetail(FirebaseUser user){
        this.user = user;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }


}
