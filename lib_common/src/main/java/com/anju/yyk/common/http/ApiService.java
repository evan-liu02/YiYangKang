package com.anju.yyk.common.http;

import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.AccidentDetailResponse;
import com.anju.yyk.common.entity.response.AttentionCountResponse;
import com.anju.yyk.common.entity.response.CareDetailResponse;
import com.anju.yyk.common.entity.response.CheckRoomDetailResponse;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.common.entity.response.HuLiXiangmu0Response;
import com.anju.yyk.common.entity.response.HuLiXiangmu1Response;
import com.anju.yyk.common.entity.response.NewTipsListResponse;
import com.anju.yyk.common.entity.response.PatrolResponse;
import com.anju.yyk.common.entity.response.RecordResponse;
import com.anju.yyk.common.entity.response.DiseaseResponse;
import com.anju.yyk.common.entity.response.HealthResposne;
import com.anju.yyk.common.entity.response.JisShuResponse;
import com.anju.yyk.common.entity.response.LoginResponse;
import com.anju.yyk.common.entity.response.OperationResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.entity.response.UploadFileResponse;
import com.anju.yyk.common.entity.response.UploadImageResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.leo.common.http
 * 
 * @Description Retrofit请求
 *
 * @GET 表明这是get请求
 * @POST 表明这是post请求
 * @PUT 表明这是put请求
 * @DELETE 表明这是delete请求
 * @PATCH 表明这是一个patch请求，该请求是对put请求的补充，用于更新局部资源
 * @HEAD 表明这是一个head请求
 * @OPTIONS 表明这是一个option请求
 * @HTTP 通用注解, 可以替换以上所有的注解，其拥有三个属性：method，path，hasBody
 * @Headers 用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在
 * @Header 作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头
 * @Body 多用于post请求发送非表单数据, 比如想要以post方式传递json格式数据
 * @Filed 多用于post请求中表单字段, Filed和FieldMap需要FormUrlEncoded结合使用
 * @FiledMap 和@Filed作用一致，用于不确定表单参数
 * @Part 用于表单字段, Part和PartMap与Multipart注解结合使用, 适合文件上传的情况
 * @PartMap 用于表单字段, 默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
 * <p>
 * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * 而Filed则相对简单些，通常是字符串键值对。
 * </p>
 * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * 而Filed则相对简单些，通常是字符串键值对。
 * @Path 用于url中的占位符,{占位符}和PATH只用在URL的path部分，url中的参数使用Query和QueryMap代替，保证接口定义的简洁
 * @Query 用于Get中指定参数
 * @QueryMap 和Query使用类似
 * @Url 指定请求路径
 *
 * 开放免费API地址 http://api.comin.top/
 *
 * @Date 2019/5/7 11:28
 * 
 * @modify:
 */
public interface ApiService {

    /**
     * 用户登录
     * @param action hugong
     * @param account 用户名
     * @param password 密码
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<LoginResponse> login(@Query("action") String action, @Query("account") String account
            , @Query("password") String password);

    /**
     * 注意事项数量请求
     * @param action zhuyicount
     * @param hugong_id 护工id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<AttentionCountResponse> attentionCount(@Query("action") String action, @Query("hugong_id") String hugong_id);

    /**
     * 置顶人员
     * @param action zhiding
     * @param hugong_id 护工id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<PersonListResponse> personTop(@Query("action") String action, @Query("hugong_id") String hugong_id);

    /**
     * 置顶状态改变
     * @param action zhiding_add
     * @param hugong_id 护工id
     * @param laoren_id 老人id
     * @param status 0置顶1取消
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> contralTop(@Query("action") String action, @Query("hugong_id") String hugong_id
                        , @Query("laoren_id") String laoren_id, @Query("status") int status);

    /**
     * 首页顶部注意事项
     * @param action zhuyi
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<NewTipsListResponse> tips(@Query("action") String action, @Query("hugong_id") String hugong_id);

    /**
     * 个人提醒列表
     * @param action
     * @param hugong_id
     * @param laoren_id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<NewTipsListResponse> personalTips(@Query("action") String action, @Query("hugong_id") String hugong_id
                            , @Query("laoren_id") String laoren_id);

    /**
     * 住院人员信息
     * @param action quanbu
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<PersonListResponse> personInfo(@Query("action") String action, @Query("hugong_id") String hugong_id);

    /**
     * @param action jiashu
     * 家属信息
     * @param id 老人id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<JisShuResponse> familyInfo(@Query("action") String action, @Query("id") String id);

    /**
     * 健康信息
     * @param action jiankang
     * @param id 老人id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<HealthResposne> healthInfo(@Query("action") String action, @Query("id") String id);

    /**
     * 疾病信息
     * @param action jibing
     * @param id 老人id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<DiseaseResponse> diseaseInfo(@Query("action") String action, @Query("id") String id);

    /**
     * 手术信息
     * @param action shoushu
     * @param id 老人id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<OperationResponse> operationInfo(@Query("action") String action, @Query("id") String id);

    /**
     * 护理记录
     * @param action
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<RecordResponse> careRecordList(@Query("action") String action, @Query("laoren") String id,
                                              @Query("time1") String startTime, @Query("time2") String endTime);

    /**
     * 护理详情
     * @param action
     * @param laoren_id
     * @param time
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<CareDetailResponse> careDetail(@Query("action") String action, @Query("laoren") String laoren_id
                                            , @Query("time") String time);

    /**
     * 事故详情
     * @param action
     * @param shigu_id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<AccidentDetailResponse> accidentDetail(@Query("action") String action, @Query("shigu_id") String shigu_id);

    /**
     * 查房记录
     * @param action
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<RecordResponse> checkRoomRecordList(@Query("action") String action, @Query("hugong_id") String id,
                                              @Query("time1") String startTime, @Query("time2") String endTime);
    /**
     * 事故记录
     * @param action
     * @param id
     * @param startTime
     * @param endTime
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<RecordResponse> accidentRecordList(@Query("action") String action, @Query("hugong_id") String id,
                                              @Query("time1") String startTime, @Query("time2") String endTime);

    /**
     * 护理项目（提交项）
     * @param action
     * @param id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<HuLiXiangmu0Response> hulixiangmu0List(@Query("action") String action, @Query("laoren_id") String id);

    /**
     * 护理项目（已完成）
     * @param action
     * @param id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<HuLiXiangmu1Response> hulixiangmu1List(@Query("action") String action, @Query("laoren_id") String id);

    /**
     * 护理项目登记
     * @param action
     * @param id 护理项目id
     * @param jifen 积分
     * @param title 护理项目标题
     * @param laoren_id 老人id
     * @param hugong_id 护工id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> hulidengji(@Query("action") String action, @Query("id") String id, @Query("jifen")
                                        String jifen, @Query("title") String title, @Query("laoren_id") String laoren_id,
                                        @Query("hugong_id") String hugong_id);

    /**
     * 查房登记列表
     * @param action
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<CheckRoomListResponse> checkRoomList(@Query("action") String action);

    /**
     * 查房项目提交
     * @param action
     * @param hugong_id
     * @param
     * @param isCheck 1选  0未选
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> checkRoomCommit(@Query("action") String action, @Query("hugong_id") String hugong_id
            , @Query("txtContent") String txtContent, @Query("isCheck") int isCheck, @Query("laoren_id") String laoren_id
            , @QueryMap Map<String, String> map);

    /**
     * 查房信息
     * @param action chafang
     * @param hugong_id 护工id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<RecordResponse> lookRoundInfo(@Query("action") String action, @Query("hugong_id") String hugong_id,
                                             @Query("time1") String startDate, @Query("time2") String endDate);

    /**
     * 查房详细信息
     * @param action chafang_show
     * @param chafang_id 信息id
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<CheckRoomDetailResponse> lookRoundInfoDetail(@Query("action") String action, @Query("chafang_id") String chafang_id);

    /**
     * 事故信息
     * @param action shigu
     * @param id 信息id
     * @param personId 老人id
     * @param time 登记时间
     * @param content 事故详情
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<String> accidentInfo(@Query("action") String action, @Query("id") int id,
                                           @Query("laoren") String personId, @Query("time") String time,
                                    @Query("content") String content);

    /**
     * 安全巡查项目
     * @param action
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<PatrolResponse> patrol(@Query("action") String action);

    /**
     * 上传音频
     * @param des
     * @param file
     * @return
     */
    @Multipart
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> uploadAudio(@Part("description") RequestBody des, @Part MultipartBody.Part file);

    /**
     * 上传音频
     * @param file
     * @return
     */
    @Multipart
    @POST(ApiAddr.UPLOAD_PATH)
    Observable<UploadFileResponse> uploadAudio(@Query("action") String action, @Query("name") String name, @Part MultipartBody.Part file);

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Multipart
    @POST(ApiAddr.UPLOAD_PATH)
    Observable<UploadFileResponse> uploadFile(@Query("action") String action, @Query("name") String name, @Part MultipartBody.Part file);

    /**
     * 上传图片
     * @param file
     * @return
     */
    @Multipart
    @POST(ApiAddr.UPLOAD_PATH)
    Observable<UploadImageResponse> uploadImage(@Query("action") String action, @Query("name") String name, @Part MultipartBody.Part file);

    /**
     * 添加提醒
     * @param action
     * @param id
     * @param userId
     * @param content
     * @param tipAudioPath
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> addTip(@Query("action") String action, @Query("laoren_id") String id, @Query("hugong_id") String userId, @Query("content") String content, @Query("tipAudioPath") String tipAudioPath);

    /**
     * 添加事故
     * @param action
     * @param id
     * @param userId
     * @param content
     * @param audioPath
     * @param imagePath
     * @return
     */
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> addAccident(@Query("action") String action, @Query("laoren_id") String id, @Query("hugong_id") String userId,
                                         @Query("content") String content, @Query("audio_path") String audioPath, @Query("image_path") String imagePath);

    /**
     * 上传图片
     * @param imageFile
     * @return
     */
    @Multipart
    @POST(ApiAddr.PATH)
    Observable<BaseResponse> uploadPhoto(@Part("imageFile") MultipartBody.Part imageFile);

    /**
     * 下载文件
     * @param audioUrl
     * @return
     */
    @GET
    @Streaming
    Observable<ResponseBody> downLoadAudio(@Url String audioUrl);

    @POST(ApiAddr.PATH)
    Observable<BaseResponse> readed(@Query("action") String action, @Query("hugong_id") String hugong_id
                        , @Query("zhuyi_id") String zhuyi_id);

}
