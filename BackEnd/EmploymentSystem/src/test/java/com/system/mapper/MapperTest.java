package com.system.mapper;

import com.system.Main;
import com.system.entity.Enterprise;
import com.system.entity.HumanResource;
import com.system.entity.Info;
import com.system.entity.PositionInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-18 17:44
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Rollback(false)
public class MapperTest {

    @MockBean
    EnterpriseMapper enterpriseMapper;
    @MockBean
    HumanResourceMapper humanResourceMapper;
    @MockBean
    InfoMapper infoMapper;
    @MockBean
    PositionMapper positionMapper;


    /*
      delete from enterprise where true;
      insert into enterprise values(1,"腾讯","qq的code密匙","是一个北极的公司","科考队员","北极");
      insert into enterprise values(2,"微信","weChat的co匙","南极公司","招北极熊","南极");
      delete from info where true;
      insert into info(title,content) values("北极部正在招人，需要保洁队员","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
      insert into info(title,content) values("南极部正在招人，需要清洁队员","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
      insert into info(title,content) values("上海部正在招人，需要科学家","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
      insert into info(title,content) values("学生部正在招人，需要宇航员","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
      insert into info(title,content) values("这里正在招人，需要不是吧","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
      insert into info(title,content) values("广州正在招人，需要在北京的人","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
      insert into info(title,content) values("微信正在招人，需要广州","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
     */
    @Test
    public void enterpriseMapperTest(){
        Enterprise enterprise = enterpriseMapper.searchById(1);
        assert enterprise.getName().equals("腾讯");
        enterprise=enterpriseMapper.searchByName("微信");
        assert enterprise.getId()==2;
    }

    @Test
    public void hrMapperTest(){
        HumanResource hr1 = new HumanResource("2029","123456","lollipop",2,"保洁部","打不通的电话号","搜不到的邮件地址","微信","weChat的co匙");
        humanResourceMapper.insert(hr1);
        // 这里企业的code是错的，但是应该会通过，因为根本没验证
        HumanResource hr2 = new HumanResource("2030","123456","lollipop2",1,"保洁部","打不通的电话号","搜不到的邮件地址","微信","weChat的co匙");
        humanResourceMapper.insert(hr2);
        String newPhone = "能打通的电话号";
        hr2.setPhone(newPhone);
        humanResourceMapper.update(hr2);
        HumanResource hr3 = humanResourceMapper.search(hr2.getId());
        assert hr3.getPhone().equals(newPhone);
    }

    @Test
    public void infoMapperTest(){
        List<Info> infos=infoMapper.searchAll();
        assert infos.size()==7;
        Info info = infoMapper.searchById(1L);
        assert info!=null;
        info = infoMapper.searchById(100L);
        assert info==null;
    }
    @Test
    public void positionMapperTest(){
        PositionInfo positionInfo = new PositionInfo("科考队员","腾讯",1,"北极部正在招人，需要保洁队员",
                "听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...","薪资待遇：可以抚摸企鹅","2029",'0');
        Long positionId = positionMapper.postPosition(positionInfo);
        assert positionId!=null;
        PositionInfo positionInfo111 = positionMapper.getPositionInfo(positionId);
        assert positionInfo111!=null;
        assert positionInfo111.getId()==positionId;

        positionMapper.updateView(positionId);
        positionInfo111 = positionMapper.getPositionInfo(positionId);
        assert positionInfo111.getViews()>0;

        PositionInfo positionInfo2 = new PositionInfo("招北极熊","微信",2,"微信正在招北极熊。",
                "听过微信正在招北极熊，这是怎么回事呢？下面请小编带大家一起来...","薪资待遇：可以吃企鹅","2030",'2');
        List<PositionInfo> positions = positionMapper.search("正在","招北极熊",'2');
        assert positions!=null;

        String newContent="修改后的内容";
        positionInfo2.setContent(newContent);
        positionMapper.updatePosition(positionInfo2);
        PositionInfo positionInfo3 =positionMapper.getPositionInfo(positionInfo2.getId());
        assert positionInfo3.getId()==positionInfo2.getId();

        List<PositionInfo> positions2 = positionMapper.findFromEnterprise(1);
        assert positions2!=null;
        assert positions.size()!=0;

        PositionInfo positionInfo4 = new PositionInfo("科考队","微信",2,"微信正在招科考队。",
                "听过微信正在招北极熊，这是怎么回事呢？下面请小编带大家一起来...","薪资待遇：可以吃企鹅","2030",'2');
        positionMapper.postPosition(positionInfo4);
        List<PositionInfo> positions3 = positionMapper.recommend("科考队",1);
        assert positions3!=null;
        assert positions3.size()==1;
    }

}
