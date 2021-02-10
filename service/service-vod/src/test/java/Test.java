import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/26 10:51
 */

public class Test {

    public static void main(String[] args) throws ClientException {

        DefaultAcsClient client = InitObject.initVodClient("LTAI4G531F8so6gVSZ9GyynT", "haym9CgnuhBaNbj4XlBM9OLMZ3SwN4");
//
//        GetPlayInfoRequest request = new GetPlayInfoRequest();
//        GetPlayInfoResponse response = new GetPlayInfoResponse();
//
//
//        request.setVideoId("3942d80012994af582cb4b4a4962ee19");
//
//
//         response = client.getAcsResponse(request);
//
//        List<GetPlayInfoResponse.PlayInfo> infoList = response.getPlayInfoList();
//
//        for (GetPlayInfoResponse.PlayInfo in:infoList) {
//
//            System.out.println("URL:  "+in.getPlayURL());
//
//        }
//        System.out.println("Title:  "+response.getVideoBase().getTitle());



        // 获取id和凭证


        GetVideoPlayAuthRequest playAuthRequest = new GetVideoPlayAuthRequest();

        GetVideoPlayAuthResponse playAuthResponse = new GetVideoPlayAuthResponse();

        playAuthRequest.setVideoId("3942d80012994af582cb4b4a4962ee19");


         playAuthResponse = client.getAcsResponse(playAuthRequest);

        System.out.println("凭证：  "+playAuthResponse.getPlayAuth());



    }

}
