package convter;

import SfnUI.Base.FileResourceAccess;
import SfnUI.Base.X2;
import SfnUI.GUI.ZCasper;
import SfnUI.UIPlatform.IDrawLibServer;
import com.sfauto.DCSHMI.Common.Global;
import com.sfauto.DCSHMI.DCS.DCSHMIApplicatio;
import com.sfauto.DCSHMI.ECS.ECSHMIApplicatio;

import java.util.Vector;

public class SFZCasperLib
{
    public  IDrawLibServer iDrawLibServer;
    public static SFZCasperLib zcasperLib;

    private SFZCasperLib() {
        loadIDrawLibServer();
    }

    public IDrawLibServer getiDrawLibServer() {
        return iDrawLibServer;
    }

    public static SFZCasperLib getInstance(){
        if (zcasperLib == null) {
            zcasperLib = new SFZCasperLib();
        }
        return zcasperLib;
    }
    public IDrawLibServer loadIDrawLibServer() {

        X2.Initialize(FileResourceAccess.getInstance(), Global.getGlobalConfig());
        DCSHMIApplicatio localDCSHMIApplication = new DCSHMIApplicatio();
        iDrawLibServer = localDCSHMIApplication.getSupportDrawLibServer();
        ECSHMIApplicatio localECSHMIApplication = new ECSHMIApplicatio();
        IDrawLibServer iDrawLibServer2 = localECSHMIApplication.getSupportDrawLibServer();
        iDrawLibServer.getAllGroups();
        return iDrawLibServer;

    }
}
