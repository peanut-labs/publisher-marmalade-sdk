/*
java implementation of the peanutlabs extension.

Add android-specific functionality here.

These functions are called via JNI from native code.
*/
/*
 * NOTE: This file was originally written by the extension builder, but will not
 * be overwritten (unless --force is specified) and is intended to be modified.
 */
import com.ideaworks3d.marmalade.LoaderAPI;
import com.ideaworks3d.marmalade.LoaderActivity;
import com.peanutlabs.plsdk.*;

// Comment in the following line if you want to use ResourceUtility
// import com.ideaworks3d.marmalade.ResourceUtility;

class peanutlabs
{
    public int openRewardsCenter(String user_id)
    {
		PeanutLabsManager pm = PeanutLabsManager.getInstance();
		
		pm.setUserId(user_id);
		pm.openRewardsCenter(LoaderAPI.getActivity());

        return 0;
    }
}
