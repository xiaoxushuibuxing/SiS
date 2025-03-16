package magus.util;

import SfnUI.Base.JRangeToColorPair;
import SfnUI.Base.br;
import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.DCSDriver;
import SfnUI.i18n.MyResourceManager;
import awt.*;
import com.sfauto.toolkits.utils.RM;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DriverUtils {
    private static Logger logger = Logger.getLogger(DriverUtils.class);
    public boolean isDasPoint;
    public DCSConditionColor dcsConditionColor;
    public DCSBar bar;
    public boolean isBar;

    public DCSConditionText conditionText;
    public DCSCondition showCondition;
    public String pointName;

    public static DriverUtils parseDrivers(BaseDrawOb obj)
    {
        logger.error("parseDrivers"+obj.GetDrawObType());
        DriverUtils driverUtils = null;
        Integer[] drivers = obj.getAllDriver();
        if (drivers == null || drivers.length == 0)
        {
            return null;
        }
        //DriverUtils driverUtils= new DriverUtils();
        for (Integer driverTypeId : drivers)
        {
            DCSDriver driver = obj.getDriverByType(driverTypeId);
            if (driver == null) {
                return null;
            }
            if (driverTypeId == DCSDriver.DRIVER_TYPE_FILLDRIVER) {
                logger.info("DRIVER_TYPE_FILLDRIVER = 115 填充  转换为bar");
                if (driverUtils == null) {
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.h ha = (SfnUI.GUI.Driver.h) driver;
                logger.info(ha.strDriverExpr);
                String pointName = PointUtils.getPointName(driver.strDriverExpr);
                if (pointName == null || pointName.length() == 0) {
                    logger.error("pointName is null");
                    return null;
                }

                driverUtils.isBar = true;
                driverUtils.bar = new DCSBar();
                driverUtils.bar.pointName = pointName;
                ha.getProp("", RM.gs("最小值"));
                driverUtils.bar.lowLimit = Float.parseFloat(ha.getProp("", RM.gs("最小值")).toString());
                driverUtils.bar.highLimit = Float.parseFloat(ha.getProp("", RM.gs("最大值")).toString());
                byte direct= Byte.valueOf(ha.getProp("", RM.gs("填充方向")).toString());
                if (direct ==1) {
                    driverUtils.bar.direct=3;
                } else if (direct ==2) {
                    driverUtils.bar.direct=4;
                } else if (direct ==3) {
                    driverUtils.bar.direct=1;
                } else if (direct ==4) {
                    driverUtils.bar.direct=2;
                } else if (direct ==5) {
                    driverUtils.bar.direct=1;
                } else if (direct ==6) {
                    driverUtils.bar.direct=4;
                } else if (direct ==7) {
                    driverUtils.bar.direct=6;
                } else if (direct ==8) {
                    driverUtils.bar.direct=5;
                } else {
                    driverUtils.bar.direct=1;
                }
                //driverUtils.bar.direct=Byte.valueOf(ha.getProp("", RM.gs("填充方向")).toString());
                driverUtils.bar.bgColor=new DCSColor();
                driverUtils.bar.bgColor.setDefaultColor((Color)(ha.getProp("", RM.gs("填充背景"))));
            }else if (driverTypeId==driver.DRIVER_TYPE_CONDITIONDRIVER) {
                logger.info("DRIVER_TYPE_CONDITIONDRIVER = 112 背景颜色");
                if (driverUtils == null) {
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.ai ai = (SfnUI.GUI.Driver.ai) driver;
                logger.info(ai.strDriverExpr);
                logger.info(ai.getProp("", RM.gs("条件为真时颜色")));
                logger.info(ai.getProp("", RM.gs("条件为假时颜色")));
                String pointName = PointUtils.getPointName(ai.strDriverExpr);
                String conditionColor = PointUtils.ParseDxConditionColor(ai.strDriverExpr,
                        ai.getProp("", RM.gs("条件为真时颜色")),ai.getProp("", RM.gs("条件为假时颜色")));
                //rect.bgColor.setConditionColor(new DCSConditionColor(conditionColor));
                driverUtils.dcsConditionColor=(new DCSConditionColor(conditionColor));
                //rect.bgColor=bgColor;
            }else if (driverTypeId == DCSDriver.DRIVER_TYPE_ANALOGINPUT) {
                logger.info("DRIVER_TYPE_ANALOGINPUT = 109 Das点模拟量输出");
                if (driverUtils == null) {
                    driverUtils = new DriverUtils();
                }
                driverUtils.isDasPoint = true;
                driverUtils.pointName = PointUtils.getPointName(driver.strDriverExpr);

            } else if (driverTypeId == DCSDriver.DRIVER_TYPE_COLORDRIVER) {
                logger.info("DRIVER_TYPE_COLORDRIVER = 111 背景色");
                SfnUI.GUI.Driver.v va = (SfnUI.GUI.Driver.v) driver;
                String pointName = va.strDriverExpr;
                JRangeToColorPair jRangeToColorPair = (JRangeToColorPair)va.getProp("",RM.gs("属性驱动"));
                List<String> conditionColorList = new ArrayList<>();
                if (jRangeToColorPair != null) {
                    if (driverUtils == null) {
                        driverUtils = new DriverUtils();
                    }
                    for (int i = 0; i < jRangeToColorPair.getsize(); i++) {
                        //  logger.error( jRangeToColorPair.getColor(i));
                        br range = jRangeToColorPair.getRange(i);
                        //double a1 = range.a;
                        // logger.error(a1);
                        String s = PointUtils.changeConditionColor(pointName, range.a, jRangeToColorPair.getColor(i));
                        //logger.error(s);
                        conditionColorList.add(s);
                    }
                    if (conditionColorList.size() == 1) {
                        driverUtils.dcsConditionColor = (new DCSConditionColor(conditionColorList.get(0)));
                    } else if (conditionColorList.size() > 1) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("{");
                        for (String s : conditionColorList) {
                            sb.append(s);
                        }
                        sb.append("}");
                       // logger.error(sb.toString());
                        driverUtils.dcsConditionColor = (new DCSConditionColor(sb.toString()));
                    }
                }
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_DIGITOUTPUTDRIVER) {
                logger.info("DRIVER_TYPE_DIGITOUTPUTDRIVER = 113 数字量输出，文本条件");
                if (driverUtils == null){
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.u u = (SfnUI.GUI.Driver.u) driver;
                String dxChangeText = PointUtils.getDxChangeText(driver.strDriverExpr,u.getProp("", RM.gs("为1时描述")),u.getProp("", RM.gs("为0时描述")));
                if (dxChangeText!=null) {
                    driverUtils.conditionText=(new DCSConditionText(dxChangeText));
                }
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_ENABLEDRIVER) {
                logger.info("DRIVER_TYPE_ENABLEDRIVER = 114 元件允许驱动，不处理");
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_LINEFLOWDRIVER) {
                logger.info("DRIVER_TYPE_LINEFLOWDRIVER = 118 流动驱动 不处理");

            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_ROTATEDRIVER) {
                logger.info("DRIVER_TYPE_ROTATEDRIVER = 119 旋转驱动 不处理");

            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_SCATTEROUTPUTDRIVER) {
                logger.info("DRIVER_TYPE_SCATTEROUTPUTDRIVER = 120 离散量输出，文本条件");
                if (driverUtils == null){
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.ak u = (SfnUI.GUI.Driver.ak) driver;
                String pointName = u.strDriverExpr;
                SfnUI.Base.bc nT = (SfnUI.Base.bc)u.getProp("", RM.gs("枚举设置"));
                List<String> conditionTextList = new ArrayList<>();
                if (nT!=null && nT.ng()>0) {
                    for (int i = 0; i < nT.ng(); i++) {
                        int intX = nT.bA(i).intX;
                        String strX = nT.bA(i).strX;
                        String s = PointUtils.changeConditionText(pointName, intX, strX);
                        conditionTextList.add(s);
                    }
                }
                if (conditionTextList.size() == 1) {
                    driverUtils.conditionText = (new DCSConditionText(conditionTextList.get(0)));
                } else if (conditionTextList.size() > 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("{");
                    for (String s : conditionTextList) {
                        sb.append(s);
                    }
                    sb.append("}");
                    driverUtils.conditionText = (new DCSConditionText(sb.toString()));
                }
            } else if(driverTypeId == DCSDriver.DRIVER_TYPE_TRANSPARENTDRIVER) {
                logger.info("DRIVER_TYPE_TRANSPARENTDRIVER = 121 透明度驱动， 不处理");
            } else if(driverTypeId == DCSDriver.DRIVER_TYPE_VAROUTPUTDRIVER) {
                logger.info("DRIVER_TYPE_VAROUTPUTDRIVER = 122 变量输出，文本条件 不处理");
            } else if(driverTypeId == DCSDriver.DRIVER_TYPE_VERTMOVEDRIVER) {
                logger.info("DRIVER_TYPE_VERTMOVEDRIVER = 123 垂直移动驱动 不处理");
            } else if(driverTypeId == DCSDriver.DRIVER_TYPE_VERTZOOMDRIVER) {
                logger.info("DRIVER_TYPE_VERTZOOMDRIVER = 124 水平移动驱动 不处理");
            }else if (driverTypeId==driver.DRIVER_TYPE_VISIBLEDRIVER) {
                logger.info("DRIVER_TYPE_VISIBLEDRIVER = 125;  可视启动");
                if (driverUtils == null) {
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.w wa = (SfnUI.GUI.Driver.w) driver;
                logger.info(wa.strDriverExpr);
                String condition =PointUtils.changeShowCondition(wa.strDriverExpr);
                logger.info(condition);
                driverUtils.showCondition=(new DCSCondition(condition));
                //rect.bgColor=bgColor;
            } else if(driverTypeId == DCSDriver.DRIVER_TYPE_TEXTCOLORDRIVER) {//多文本驱动
                //String pointName = PointUtils.getPointName(driver.strDriverExpr);
                logger.info("DRIVER_TYPE_MULTICONDITIONTEXT = 126 文本色驱动");
                if (driverUtils == null){
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.v u = (SfnUI.GUI.Driver.v) driver;
                SfnUI.Base.JRangeToColorPair cv = (SfnUI.Base.JRangeToColorPair) u.getProp("", RM.gs("属性驱动"));

            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_LINEDRIVER) {//线色驱动
                //String pointName = PointUtils.getPointName(driver.strDriverExpr);
                logger.info("DRIVER_TYPE_LINEDRIVER = 127 线色驱动");
                if (driverUtils == null){
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.v u = (SfnUI.GUI.Driver.v) driver;
                SfnUI.Base.JRangeToColorPair cv = (SfnUI.Base.JRangeToColorPair) u.getProp("", RM.gs("属性驱动"));
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_ANIROTATEDRIVER) {//动画旋转驱动
               logger.info("DRIVER_TYPE_ANIROTATEDRIVER = 130 动画旋转驱动不处理");
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_OBJECTDRIVER) {//动画旋转驱动
                logger.info("DRIVER_TYPE_OBJECTDRIVER = 142 对象驱动不处理");
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_MULTICONDITIONLINECOLOR) {//多条件线颜色驱动
                logger.info("DRIVER_TYPE_MULTICONDITIONLINECOLOR = 144 多条件线颜色驱动");
                if (driverUtils == null)
                    driverUtils = new DriverUtils();
                SfnUI.GUI.Driver.MultiConditionColorDriver u = (SfnUI.GUI.Driver.MultiConditionColorDriver) driver;
                SfnUI.GUI.Driver.MultiConditionColors cv = (SfnUI.GUI.Driver.MultiConditionColors) u.getProp("", MyResourceManager.CC_MultiConditionColor);
                Vector iterator = cv.mccs;
                String str="{";
                for (Object objc : iterator) {
                    SfnUI.GUI.Driver.MultiConditionColor ya = (SfnUI.GUI.Driver.MultiConditionColor)objc;
                    String condition = PointUtils.changeConditionColor(ya.getProp("", MyResourceManager.Expr).toString(),
                            (Color)ya.getProp("", RM.gs("颜色")));
                    if (condition!=null) {
                        str+=condition;
                    }
                }
                str+="}";
                driverUtils.dcsConditionColor = new DCSConditionColor(str);
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_MULTICONDITIONFILLCOLOR) {//多条件填充颜色驱动
                logger.info("DRIVER_TYPE_MULTICONDITIONFILLCOLOR = 145 多条件填充颜色驱动");
                if (driverUtils == null)
                    driverUtils = new DriverUtils();
                SfnUI.GUI.Driver.MultiConditionColorDriver u = (SfnUI.GUI.Driver.MultiConditionColorDriver) driver;
                SfnUI.GUI.Driver.MultiConditionColors cv = (SfnUI.GUI.Driver.MultiConditionColors) u.getProp("", MyResourceManager.CC_MultiConditionColor);
                Vector iterator = cv.mccs;
                String str="{";
                for (Object objc : iterator) {
                    SfnUI.GUI.Driver.MultiConditionColor ya = (SfnUI.GUI.Driver.MultiConditionColor)objc;

                    String condition = PointUtils.changeConditionColor(ya.getProp("", MyResourceManager.Expr).toString(),
                            (Color)ya.getProp("", RM.gs("颜色")));
                    if (condition!=null) {
                        str+=condition;
                    }
                }
                str+="}";
                driverUtils.dcsConditionColor = new DCSConditionColor(str);
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_MULTICONDITIONTEXTCOLOR) {//文本颜色条件
                logger.info("DRIVER_TYPE_MULTICONDITIONTEXTCOLOR = 146 文本颜色条件");
                if (driverUtils == null)
                    driverUtils = new DriverUtils();
                SfnUI.GUI.Driver.MultiConditionColorDriver u = (SfnUI.GUI.Driver.MultiConditionColorDriver) driver;
                SfnUI.GUI.Driver.MultiConditionColors cv = (SfnUI.GUI.Driver.MultiConditionColors) u.getProp("", MyResourceManager.CC_MultiConditionColor);
                Vector iterator = cv.mccs;
                String str="{";
                for (Object objc : iterator) {
                    SfnUI.GUI.Driver.MultiConditionColor ya = (SfnUI.GUI.Driver.MultiConditionColor)objc;

                    String condition = PointUtils.changeConditionColor(ya.getProp("", MyResourceManager.Expr).toString(),
                            (Color)ya.getProp("", RM.gs("颜色")));
                    if (condition!=null) {
                        str+=condition;
                    }
                }
                str+="}";

                driverUtils.dcsConditionColor = new DCSConditionColor(str);
            }else if(driverTypeId == DCSDriver.DRIVER_TYPE_MULTICONDITIONTEXT) {//多文本驱动
                logger.info("DRIVER_TYPE_MULTICONDITIONTEXT = 147 多文本驱动");
                if (driverUtils == null){
                    driverUtils = new DriverUtils();
                }
                SfnUI.GUI.Driver.p u = (SfnUI.GUI.Driver.p) driver;
                SfnUI.GUI.Driver.c cv = (SfnUI.GUI.Driver.c) u.getProp("", MyResourceManager.CC_MultiConditionText);
                Vector iterator = cv.XS;
                String str="{";
                for (Object objc : iterator) {
                    SfnUI.GUI.Driver.y ya = (SfnUI.GUI.Driver.y)objc;
                    String condition = PointUtils.changeMoerTextCondition(ya.getProp("", MyResourceManager.Expr),
                            ya.getProp("", RM.gs("内容")));
                    if (condition!=null) {
                        str+=condition;
                    }
                }
                str+="}";
                driverUtils.conditionText = new DCSConditionText(str);
            } else if(driverTypeId == DCSDriver.DRIVER_TYPE_BLINKDRIVER) {
                logger.info(driver.getDriverExpr());
                logger.info("DRIVER_TYPE_BLINKDRIVER = 110; 闪烁条件系统不处理");
            } else {
                logger.error("driverTypeId is not parse:"+driverTypeId);
            }

        }
        return driverUtils;
    }
}
