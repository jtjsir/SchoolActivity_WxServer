package com.du.wx.util;

import com.du.wx.model.Item;
import com.du.wx.model.common.BaseOutMessage;
import com.du.wx.model.input.SummaryInputMessage;
import com.du.wx.model.out.ArticleOutMessage;
import com.du.wx.model.out.MusicOutMessage;
import com.du.wx.model.out.PicOutMessage;
import com.du.wx.model.out.TextOutMessage;
import com.du.wx.model.out.VideoOutMessage;
import com.du.wx.model.out.VoiceOutMessage;
import com.du.wx.model.type.ReqAndResp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jing
 */
public class MessageUtil {
    //是否启用图灵机器人 0代表不启用 1代表启用
    public static int code = 0 ;

    //封裝XML格式数据
    public static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 判断是否为QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        //判断表情的的正则表达式
        StringBuilder qqfaceRegix = new StringBuilder();
        qqfaceRegix.append("/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--")
                .append("b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?")
                .append("|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-")
                .append("\\)|/::\\*|/:@x|/:8\\*|/:pd|/:")
                .append("<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:")
                .append("<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>");
        Pattern pattern = Pattern.compile(qqfaceRegix.toString());
        Matcher m = pattern.matcher(content);
        if (m.matches()) {
            result = true;
        }

        return result;
    }

    /**
     * emoji表情的转码
     *
     * @param hex
     * @return
     */
    public static String emoji(int hex) {
        return String.valueOf(Character.toChars(hex));
    }
    
    /**
     * 处理微信传过来的数据解析
     * @param request
     * @return
     * @throws IOException
     */
    public static SummaryInputMessage readMessge(InputStream in) throws IOException {
        SummaryInputMessage msg = null;
        //映射
        xstream.alias("xml", SummaryInputMessage.class);

        msg = (SummaryInputMessage) xstream.fromXML(in);

        in.close();

        return msg;
    }
    
    /**
     * 根据返回类型处理消息
     * @param outMessage
     * @param resqType
     * @return
     * @throws IOException
     */
    public static String handMsg(BaseOutMessage outMessage,ReqAndResp.respType respType) throws IOException{
    	switch (respType) {
		case text:
			xstream.alias("xml", TextOutMessage.class);
			break;
		case image:
			xstream.alias("xml", PicOutMessage.class);
			break ;
		case voice:
			xstream.alias("xml", VoiceOutMessage.class);
			break ;
		case music:
			xstream.alias("xml", MusicOutMessage.class);
			break ;
		case news:
			xstream.alias("item", Item.class);
			xstream.alias("xml", ArticleOutMessage.class);
			break ;
		case video:
			xstream.alias("xml", VideoOutMessage.class);
		default:
			xstream.alias("xml", TextOutMessage.class);
			break;
		}
    	return xstream.toXML(outMessage) ;
    }
    
    public static void main(String[] args){
    }
}
