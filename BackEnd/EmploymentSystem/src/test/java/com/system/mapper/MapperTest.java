//package com.system.mapper;
//
//import com.system.Main;
//import com.system.entity.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * @program: EmploymentSystem
// * @description:
// * @author: LiLi
// * @create: 2021-06-18 17:44
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Main.class})
//@Rollback()
//public class MapperTest {
//
//    @Autowired
//    EnterpriseMapper enterpriseMapper;
//    @Autowired
//    HumanResourceMapper humanResourceMapper;
//    @Autowired
//    InfoMapper infoMapper;
//    @Autowired
//    PositionMapper positionMapper;
//    @Autowired
//    ResumeMapper resumeMapper;
//    @Autowired
//    UserInfoMapper userInfoMapper;
//
//
//    /*
//      delete from enterprise where true;
//      insert into enterprise values(1,"腾讯","qq的code密匙","是一个北极的公司","科考队员","北极");
//      insert into enterprise values(2,"微信","weChat的co匙","南极公司","招北极熊","南极");
//      delete from info where true;
//      insert into info(title,content) values("北极部正在招人，需要保洁队员","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      insert into info(title,content) values("南极部正在招人，需要清洁队员","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      insert into info(title,content) values("上海部正在招人，需要科学家","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      insert into info(title,content) values("学生部正在招人，需要宇航员","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      insert into info(title,content) values("这里正在招人，需要不是吧","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      insert into info(title,content) values("广州正在招人，需要在北京的人","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      insert into info(title,content) values("微信正在招人，需要广州","听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...");
//      delete from positionInfo where true;
//      delete from humanResource where true;
//     */
//
//    @Test
//    public void test(){
////        enterpriseMapperTest();
////        hrMapperTest();
////        positionMapperTest();
//        resumeMapperTest();
//        userMapperTest();
//    }
//
//    public void enterpriseMapperTest(){
//        Enterprise enterprise = enterpriseMapper.searchById(1);
//        assert enterprise.getName().equals("腾讯");
//        enterprise=enterpriseMapper.searchByName("微信");
//        assert enterprise.getId()==2;
//    }
//
//    public void hrMapperTest(){
//        HumanResource hr1 = new HumanResource("2029","123456","loll",2,"保洁部","打不通的电话号","搜不到的邮件地址","微信","weChat的co匙");
//        humanResourceMapper.insert(hr1);
//        // 这里企业的code是错的，但是应该会通过，因为根本没验证
//        HumanResource hr2 = new HumanResource("2030","123456","loll2",1,"保洁部","打不通的电话号","搜不到的邮件地址","微信","weChat的co匙");
//        humanResourceMapper.insert(hr2);
//        String newPhone = "能打通的电话号";
//        hr2.setPhone(newPhone);
//        humanResourceMapper.update(hr2);
//        HumanResource hr3 = humanResourceMapper.search(hr2.getId());
//        assert hr3.getPhone().equals(newPhone);
//    }
//
//    public void positionMapperTest(){
//        PositionInfo positionInfo = new PositionInfo("科考队员","腾讯",1,"北极部正在招人，需要保洁队员",
//                "听过北极部正在招人，这是怎么回事呢？下面请小编带大家一起来...","可以摸企鹅","2029",'0');
//        positionMapper.postPosition(positionInfo);
//        Long positionId = positionInfo.getId();
//        System.out.println(positionInfo.getId());
//        assert positionId!=null;
//        PositionInfo positionInfo111 = positionMapper.getPositionInfo(positionId);
//        assert positionInfo111!=null;
//        assert positionInfo111.getId()==positionId;
//
//        positionMapper.updateView(positionId);
//        positionInfo111 = positionMapper.getPositionInfo(positionId);
//        assert positionInfo111.getViews()>0;
//
//        PositionInfo positionInfo2 = new PositionInfo("招北极熊","微信",2,"微信正在招北极熊。",
//                "听过微信正在招北极熊，这是怎么回事呢？下面请小编带大家一起来...","薪资待遇：可以吃企鹅","2030",'2');
//        List<PositionInfo> positions = positionMapper.search("正在","招北极熊",'2');
//        assert positions!=null;
//
//        String newContent="修改后的内容";
//        positionInfo2.setContent(newContent);
//        positionMapper.postPosition(positionInfo2);
//        PositionInfo positionInfo3 =positionMapper.getPositionInfo(positionInfo2.getId());
//        assert positionInfo3!=null;
//        assert positionInfo2!=null;
//        assert positionInfo3.getId()==positionInfo2.getId();
//
//        List<PositionInfo> positions2 = positionMapper.findFromEnterprise(1);
//        assert positions2!=null;
//        assert positions2.size()!=0;
//
//        PositionInfo positionInfo4 = new PositionInfo("科考队","微信",2,"微信正在招科考队。",
//                "听过微信正在招北极熊，这是怎么回事呢？下面请小编带大家一起来...","薪资待遇：可以吃企鹅","2030",'2');
//        positionMapper.postPosition(positionInfo4);
//        List<PositionInfo> positions3 = positionMapper.recommend("科考队",1);
//        assert positions3!=null;
//        assert positions3.size()==1;
//    }
//
//    /*
//      之前执行
//    * INSERT INTO userInfo VALUES('b001','123456','芝士西瓜','a','111','1234','21','java','A盘');
//      INSERT INTO userInfo VALUES('b002','456789','葡萄啵啵','b','222','5678','31','python','B盘');
//      INSERT INTO RESUME VALUES(5,'b001','E盘',FALSE);
//      INSERT INTO RESUME VALUES(5,'b002','F盘',FALSE);
//      INSERT INTO RESUME VALUES(6,'b001','G盘',TRUE);
//      之后执行
//      DELETE FROM RESUME WHERE positionId = 6 AND userId = "b002";
//      UPDATE RESUME SET resume = "E盘",isDealed = FALSE WHERE positionId = 5 AND userId = "b001";
//      UPDATE RESUME SET isDealed = FALSE WHERE positionId = 5 AND userId = "b002";
//      UPDATE userInfo SET pswd="456789",name="葡萄啵啵",IDNO="b",phone="222",email="5678",age="31",interest="python" WHERE id = "b002";
//      UPDATE userInfo SET resume="B盘" WHERE id = "b002";
//      DELETE FROM RESUME WHERE positionId = 5 AND userId = "b001";
//      DELETE FROM RESUME WHERE positionId = 5 AND userId = "b002";
//      DELETE FROM RESUME WHERE positionId = 6 AND userId = "b001";
//      DELETE FROM userInfo WHERE id = "b001";
//      DELETE FROM userInfo WHERE id = "b002";
//      DELETE FROM userInfo WHERE id = "b003";
//      */
//    public void resumeMapperTest(){
//        List<Resume> resumes = resumeMapper.searchByUserId("b001");
//        assert resumes.size() == 2;
//        assert resumes.get(0).getResume().equals("E盘");
//        assert resumes.get(1).isDealed();
//
//        List<Resume> resumes1 = resumeMapper.searchByPositionId(5);
//        assert resumes1.size() == 2;
//        assert resumes1.get(0).getUserId().equals("b001");
//        assert resumes1.get(1).getResume().equals("F盘");
//
//        Resume resume = resumeMapper.searchByIds(new ResumeId(6, "b001"));
//        assert resume.getResume().equals("G盘");
//        assert resume.isDealed();
//
//        Resume resume1 = new Resume();
//        resume1.setPositionId(6);
//        resume1.setUserId("b002");
//        resume1.setResume("H盘");
//        resume1.setDealed(false);
//        resumeMapper.insertResume(resume1);
//
//        Resume resume2 = new Resume();
//        resume2.setPositionId(5);
//        resume2.setUserId("b001");
//        resume2.setResume("aaa");
//        resumeMapper.updateResume(resume2);
//
//        Resume resume3 = new Resume();
//        resume3.setPositionId(5);
//        resume3.setUserId("b002");
//        resume3.setDealed(true);
//        resumeMapper.modifyDeal(resume3);
//    }
//
//    public void userMapperTest(){
//        UserInfo user = userInfoMapper.search("b001");
//        assert user.getName().equals("芝士西瓜");
//        assert user.getEmail().equals("1234");
//        assert user.getInterest().equals("java");
//
//        UserInfo user1 = new UserInfo();
//        user1.setId("b003");
//        user1.setPswd("aaa123");
//        user1.setName("芒果卡士");
//        user1.setIDNO("c");
//        user1.setPhone("333");
//        userInfoMapper.insert(user1);
//
//        UserInfo user2 = new UserInfo();
//        user2.setId("b002");
//        user2.setPswd("bbb");
//        user2.setName("荔枝酸奶");
//        user2.setIDNO("bb");
//        user2.setPhone("222222");
//        user2.setEmail("56785678");
//        user2.setAge(28);
//        user2.setInterest("go");
//        userInfoMapper.update(user2);
//
//        String resume = userInfoMapper.searchResume("b001");
//        assert resume.equals("A盘");
//
//        UserInfo user3 = new UserInfo();
//        user3.setId("b002");
//        user3.setResume("BCD盘");
//        userInfoMapper.updateResume(user3);
//    }
//}
