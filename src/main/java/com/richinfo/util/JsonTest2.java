package com.richinfo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.richinfo.annotation.po.*;

import java.util.ArrayList;
import java.util.List;

public class JsonTest2 {
    public static void main(String[] args){
//            test1();
//            test3();
//        test4();
        carbrand();
    }
    public static void test4(){
        CarsData carsData = new CarsData();
        Cardata cardata = new Cardata();
        cardata.setBrand("车型：荣威");
        cardata.setColor("红");
        cardata.setCarno("鲁L00123");
        cardata.setGoods("特殊物品");
        Cardata cardata1 = new Cardata();
        cardata1.setBrand("车型：奔驰");
        cardata1.setColor("黑");
        cardata1.setCarno("鲁L0D123");
        cardata1.setGoods("特殊物品1");
        Cardata cardata2 = new Cardata();
        cardata2.setBrand("车型：宝马");
        cardata2.setColor("红");
        cardata2.setCarno("鲁L32F23");
        cardata2.setGoods("特殊物品2");
        List<Cardata> cardataList = new ArrayList<>();
        cardataList.add(cardata);
        cardataList.add(cardata1);
        cardataList.add(cardata2);
        carsData.setCars(cardataList);
        carsData.setFilename("000021.jpg");
        String jsonString = JSONObject.toJSONString(carsData);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);
    }
    public static void test1(){
        Record  record = new Record();

        record.setCasename("案件名称");
        record.setFilename("/recorddoc/案件名称/100002.doc");
        record.setRecordname("丁北玉吸毒贩毒案");
        record.setStarttime("2016年1月29日13时13分");
        record.setEndtime("2016年1月29日14时01分");

        Person person = new Person();
        person.setName("丁北玉");
        person.setAlias("老丁头");
        person.setAge("27岁");
        person.setSex("男");
        person.setBirthday("1989年8月21日");

        Identify identify = new Identify();
        identify.setNo("370284198908214358");
        identify.setType("居民身份证");
        person.setIdentify(identify);

        person.setEducation("文化程度小学");
        person.setWork("工作日照办厂");
        person.setTag("标签惯犯");
        person.setDomicile("户籍地址xxxxxx");
        person.setOrigin("籍贯（山东省日照市）");
        person.setPlace("居住地（日照xx区，xx村）");

        //Contactlist
        List<Contact> contacts0 = new ArrayList<Contact>();
        Contact contact0= new Contact();
        contact0.setName("丁北玉");
        contact0.setType("手机号");
        contact0.setNo("13964240123");
        contacts0.add(contact0);

        Contact contact4= new Contact();
        contact4.setName("威武将军（昵称）");
        contact4.setType("微信");
        contact4.setNo("j2323123222（微信号）");
        contacts0.add(contact4);

        person.setContacts(contacts0);

        //Familylist
        List<Family> families = new ArrayList<Family>();
        Family family = new Family();
        family.setName("刘瑞巧");
        family.setRelation("母子");
        families.add(family);

        Family family1 = new Family();
        family1.setName("丁福亲");
        family1.setRelation("父子");
        families.add(family1);

        person.setFamilys(families);






        //笔录关联人列表
        List<Person> personList =new ArrayList<>();

        Person person1 = new Person();
        person1.setName("刘XX");
        Contact contact= new Contact();
        contact.setName("刘XX");
        contact.setType("手机号");
        contact.setNo("13964240869");

        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(contact);

        person1.setContacts(contacts);
        person1.setWork("务农");
        person1.setAge("49岁");
        personList.add(person1);

        Person person2 = new Person();
        person2.setName("丁凯");
        List<Contact> contacts2 = new ArrayList<Contact>();
        Contact contact2= new Contact();
        contact2.setName("丁凯");
        contact2.setType("手机号");
        contact2.setNo("13188181444");
        contacts2.add(contact2);

        Contact contact3= new Contact();
        contact3.setName("我叫丁丁（昵称）");
        contact3.setType("微信");
        contact3.setNo("j123222（微信号）");
        contacts2.add(contact3);

        person2.setContacts(contacts2);
        person2.setTag("东北人");
        person2.setAge("27岁");
        personList.add(person2);

        record.setPersonlist(personList);

        Relation relation3 =new Relation();
        relation3.setName("丁凯");
        relation3.setRelation("同学");


        List<Relation> relations3 = new ArrayList<Relation>();
        relations3.add(relation3);
        person1.setRelations(relations3);

        //关系列表
        List<Relation> relations1 = new ArrayList<Relation>();
        Relation relation1 =new Relation();
        relation1.setName("刘XXX");
        relation1.setRelation("同事");
        relations1.add(relation1);

        Relation relation2 =new Relation();
        relation2.setName("丁凯");
        relation2.setRelation("同吸");

        relations1.add(relation2);

        person.setRelations(relations1);
        record.setPerson(person);

        //事件列表
        List<Event> events =new ArrayList<Event>();

        Event event1 = new Event();
        event1.setType("带货");
        event1.setTime("2015年8月5日 玩午");
        event1.setPlace("日照市XXX高速公里");
        //参与人列表
        List<EventPerson> persons = new ArrayList<EventPerson>();
        EventPerson eperson = new EventPerson();
        eperson.setName("张哥");
        eperson.setType("买方");
        persons.add(eperson);

        EventPerson eperson1 = new EventPerson();
        eperson1.setName("丁凯");
        eperson1.setType("卖方");
        persons.add(eperson1);
        event1.setPersons(persons);
        events.add(event1);

        Event event2 = new Event();
        event2.setTime("2015年12月5日 上午");
        event2.setPlace("日照市XXX宾馆");
        event2.setType("吸毒贩毒");
        //参与人列表
        List<EventPerson> persons2 = new ArrayList<EventPerson>();
        EventPerson eperson2 = new EventPerson();
        eperson2.setName("老马");
        eperson2.setType("卖方");
        persons2.add(eperson2);
        EventPerson eperson3 = new EventPerson();
        eperson3.setName("孙凯");
        eperson3.setType("吸毒者");
        persons2.add(eperson3);

        event2.setPersons(persons2);

        event2.setTag("本地人员涉案");

        events.add(event2);
        record.setEvents(events);

        String jsonString = JSONObject.toJSONString(record);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);
    }
    public static void test2(){
        Record  record = new Record();
        List<Person> personList = new ArrayList<>();
        record.setPersonlist(personList);
        record.setStarttime("");
        record.setEndtime("");
        record.setRecordname("");
        record.setType("");
        List<Event> eventList = new ArrayList<>();
        record.setEvents(eventList);
        record.setTag("");

        Person person = new Person();
        List<Family> familyList = new ArrayList<>();
        person.setFamilys(familyList);
        List<Relation> relationList = new ArrayList<>();
        person.setRelations(relationList);
        List<Contact> contactList = new ArrayList<>();
        person.setContacts(contactList);
        person.setNation("");
        person.setSex("");
        person.setPlace("");
        person.setOrigin("");
        Identify identify = new Identify();
        identify.setType("");
        identify.setNo("");
        person.setIdentify(identify);

        person.setEducation("");

        person.setDomicile("");
        person.setBirthday("");
        person.setAlias("");
        person.setWork("");
        person.setName("");
        person.setAge("");

        record.setPerson(person);

        String jsonString = JSONObject.toJSONString(record);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);
    }

    public static void test3(){
        String jsonstr ="{\"recordname\":\"\",\"person\":{\"birthday\":\"121\",\"education\":\"\",\"identify\":{\"no\":\"\",\"type\":\"\"},\"nation\":\"\",\"work\":\"\",\"origin\":\"\",\"sex\":\"\",\"name\":\"\",\"alias\":\"\",\"domicile\":\"\",\"place\":\"\",\"familys\":[],\"relations\":[],\"age\":\"\",\"contacts\":[]},\"endtime\":\"\",\"starttime\":\"\",\"tag\":\"\",\"type\":\"\",\"personlist\":[],\"events\":[]}";

        Record record =JSONObject.parseObject(jsonstr, Record.class);
        System.out.println(record.getPerson().getBirthday());
        jsonstr="{\"filename\":\"/recorddoc/案件名称/100002.doc\",\"recordname\":\"丁北玉吸毒贩毒案\",\"person\":{\"birthday\":\"1989年8月21日\",\"education\":\"文化程度小学\",\"identify\":{\"no\":\"370284198908214358\",\"type\":\"居民身份证\"},\"work\":\"工作日照办厂\",\"origin\":\"籍贯（山东省日照市）\",\"sex\":\"男\",\"name\":\"丁北玉\",\"alias\":\"老丁头\",\"domicile\":\"户籍地址xxxxxx\",\"place\":\"居住地（日照xx区，xx村）\",\"tag\":\"标签惯犯\",\"familys\":[{\"name\":\"刘瑞巧\",\"relation\":\"母子\"},{\"name\":\"丁福亲\",\"relation\":\"父子\"}],\"relations\":[{\"name\":\"刘XXX\",\"relation\":\"同事\"},{\"name\":\"丁凯\",\"relation\":\"同吸\"}],\"age\":\"27岁\",\"contacts\":[{\"no\":\"13964240123\",\"name\":\"丁北玉\",\"type\":\"手机号\"},{\"no\":\"j2323123222（微信号）\",\"name\":\"威武将军（昵称）\",\"type\":\"微信\"}]},\"endtime\":\"2016年1月29日14时01分\",\"casename\":\"案件名称\",\"starttime\":\"2016年1月29日13时13分\",\"personlist\":[{\"work\":\"务农\",\"name\":\"刘XX\",\"relations\":[{\"name\":\"丁凯\",\"relation\":\"同学\"}],\"age\":\"49岁\",\"contacts\":[{\"no\":\"13964240869\",\"name\":\"刘XX\",\"type\":\"手机号\"}]},{\"name\":\"丁凯\",\"tag\":\"东北人\",\"age\":\"27岁\",\"contacts\":[{\"no\":\"13188181444\",\"name\":\"丁凯\",\"type\":\"手机号\"},{\"no\":\"j123222（微信号）\",\"name\":\"我叫丁丁（昵称）\",\"type\":\"微信\"}]}],\"events\":[{\"persons\":[{\"name\":\"张哥\",\"type\":\"买方\"},{\"name\":\"丁凯\",\"type\":\"卖方\"}],\"place\":\"日照市XXX高速公里\",\"time\":\"2015年8月5日 玩午\",\"type\":\"带货\"},{\"persons\":[{\"name\":\"老马\",\"type\":\"卖方\"},{\"name\":\"孙凯\",\"type\":\"吸毒者\"}],\"place\":\"日照市XXX宾馆\",\"tag\":\"本地人员涉案\",\"time\":\"2015年12月5日 上午\",\"type\":\"吸毒贩毒\"}]}";
        record =JSONObject.parseObject(jsonstr, Record.class);
        System.out.println(record.getEvents().get(0).getPersons().get(0).getName());
    }

    public static void carbrand(){
        String jsonstr="[{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_9_100.png\",\"brand\":\"奥迪\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_92_100.png\",\"brand\":\"阿尔法·罗密欧\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_97_100.png\",\"brand\":\"阿斯顿·马丁\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_268_100.png\",\"brand\":\"ALPINA\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_289_100.png\",\"brand\":\"ARCFOX\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_157_100.png\",\"brand\":\"宝骏\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_3_100.png\",\"brand\":\"宝马\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_2_100.png\",\"brand\":\"奔驰\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_26_100.png\",\"brand\":\"本田\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_5_100.png\",\"brand\":\"标致\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_127_100.png\",\"brand\":\"别克\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_15_100.png\",\"brand\":\"比亚迪\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_82_100.png\",\"brand\":\"保时捷\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_236_100.png\",\"brand\":\"宝沃\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_59_100.png\",\"brand\":\"奔腾\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_263_100.png\",\"brand\":\"比速\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_195_100.png\",\"brand\":\"北汽绅宝\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_211_100.png\",\"brand\":\"北汽幻速\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_168_100.png\",\"brand\":\"北汽威旺\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_129_100.png\",\"brand\":\"北汽昌河\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_14_100.png\",\"brand\":\"北汽制造\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_282_100.png\",\"brand\":\"北汽道达\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_216_100.png\",\"brand\":\"北汽新能源\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_163_100.png\",\"brand\":\"北京\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_85_100.png\",\"brand\":\"宾利\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_136_100.png\",\"brand\":\"长安\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_159_100.png\",\"brand\":\"长安欧尚\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_281_100.png\",\"brand\":\"长安轻型车\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_283_100.png\",\"brand\":\"长安跨越\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_21_100.png\",\"brand\":\"长城\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_8_100.png\",\"brand\":\"大众\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_197_100.png\",\"brand\":\"东风风度\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_253_100.png\",\"brand\":\"东风风光\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_141_100.png\",\"brand\":\"东风风神\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_115_100.png\",\"brand\":\"东风风行\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_205_100.png\",\"brand\":\"东风小康\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_27_100.png\",\"brand\":\"东风\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_29_100.png\",\"brand\":\"东南\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_113_100.png\",\"brand\":\"道奇\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_179_100.png\",\"brand\":\"DS\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_294_100.png\",\"brand\":\"电咖\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_7_100.png\",\"brand\":\"丰田\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_17_100.png\",\"brand\":\"福特\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_40_100.png\",\"brand\":\"菲亚特\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_91_100.png\",\"brand\":\"法拉利\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_67_100.png\",\"brand\":\"福迪\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_208_100.png\",\"brand\":\"福汽启腾\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_128_100.png\",\"brand\":\"福田\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_257_100.png\",\"brand\":\"Faraday Future\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_147_100.png\",\"brand\":\"广汽传祺\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_295_100.png\",\"brand\":\"广汽新能源\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_182_100.png\",\"brand\":\"观致\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_290_100.png\",\"brand\":\"国金\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_109_100.png\",\"brand\":\"GMC\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_196_100.png\",\"brand\":\"哈弗\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_32_100.png\",\"brand\":\"海马\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_259_100.png\",\"brand\":\"汉腾\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_58_100.png\",\"brand\":\"红旗\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_112_100.png\",\"brand\":\"华泰\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_52_100.png\",\"brand\":\"黄海\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_292_100.png\",\"brand\":\"华骐\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_225_100.png\",\"brand\":\"华颂\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_34_100.png\",\"brand\":\"吉利\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_35_100.png\",\"brand\":\"江淮\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_98_100.png\",\"brand\":\"捷豹\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_4_100.png\",\"brand\":\"Jeep\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_296_100.png\",\"brand\":\"捷途\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_37_100.png\",\"brand\":\"江铃\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_39_100.png\",\"brand\":\"金杯\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_57_100.png\",\"brand\":\"金龙\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_152_100.png\",\"brand\":\"九龙\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_279_100.png\",\"brand\":\"君马\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_107_100.png\",\"brand\":\"凯迪拉克\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_220_100.png\",\"brand\":\"凯翼\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_51_100.png\",\"brand\":\"克莱斯勒\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_150_100.png\",\"brand\":\"开瑞\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_214_100.png\",\"brand\":\"卡升\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_213_100.png\",\"brand\":\"卡威\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_94_100.png\",\"brand\":\"雷克萨斯\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_99_100.png\",\"brand\":\"雷诺\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_267_100.png\",\"brand\":\"领克\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_95_100.png\",\"brand\":\"林肯\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_36_100.png\",\"brand\":\"陆风\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_96_100.png\",\"brand\":\"路虎\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_76_100.png\",\"brand\":\"力帆\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_16_100.png\",\"brand\":\"铃木\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_80_100.png\",\"brand\":\"劳斯莱斯\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_86_100.png\",\"brand\":\"兰博基尼\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_153_100.png\",\"brand\":\"猎豹\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_18_100.png\",\"brand\":\"马自达\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_79_100.png\",\"brand\":\"名爵\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_81_100.png\",\"brand\":\"MINI\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_183_100.png\",\"brand\":\"迈凯伦\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_93_100.png\",\"brand\":\"玛莎拉蒂\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_155_100.png\",\"brand\":\"纳智捷\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_84_100.png\",\"brand\":\"讴歌\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_293_100.png\",\"brand\":\"Polestar\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_42_100.png\",\"brand\":\"奇瑞\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_156_100.png\",\"brand\":\"启辰\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_28_100.png\",\"brand\":\"起亚\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_231_100.png\",\"brand\":\"前途\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_287_100.png\",\"brand\":\"奇点汽车\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_43_100.png\",\"brand\":\"庆铃\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_30_100.png\",\"brand\":\"日产\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_78_100.png\",\"brand\":\"荣威\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_10_100.png\",\"brand\":\"斯柯达\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_111_100.png\",\"brand\":\"斯巴鲁\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_89_100.png\",\"brand\":\"smart\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_260_100.png\",\"brand\":\"SWM斯威\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_299_100.png\",\"brand\":\"SOL\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_25_100.png\",\"brand\":\"三菱\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_165_100.png\",\"brand\":\"上汽大通\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_209_100.png\",\"brand\":\"山姆\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_102_100.png\",\"brand\":\"双龙\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_189_100.png\",\"brand\":\"特斯拉\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_175_100.png\",\"brand\":\"腾势\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_48_100.png\",\"brand\":\"五菱\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_19_100.png\",\"brand\":\"沃尔沃\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_270_100.png\",\"brand\":\"WEY\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_266_100.png\",\"brand\":\"蔚来\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_132_100.png\",\"brand\":\"五十铃\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_207_100.png\",\"brand\":\"潍柴英致\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_298_100.png\",\"brand\":\"威马汽车\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_49_100.png\",\"brand\":\"雪佛兰\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_6_100.png\",\"brand\":\"雪铁龙\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_13_100.png\",\"brand\":\"现代\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_297_100.png\",\"brand\":\"小鹏汽车\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_286_100.png\",\"brand\":\"星驰\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_100_100.png\",\"brand\":\"英菲尼迪\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_258_100.png\",\"brand\":\"驭胜\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_138_100.png\",\"brand\":\"野马\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_53_100.png\",\"brand\":\"一汽\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_41_100.png\",\"brand\":\"依维柯\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_291_100.png\",\"brand\":\"裕路\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_285_100.png\",\"brand\":\"云度\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_77_100.png\",\"brand\":\"众泰\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_60_100.png\",\"brand\":\"中华\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_233_100.png\",\"brand\":\"知豆\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_203_100.png\",\"brand\":\"之诺\"},{\"picurl\":\"http://image.bitautoimg.com/bt/car/default/images/logo/masterbrand/png/100/m_33_100.png\",\"brand\":\"中兴\"}]";
        List<Carbrand> list =  JSONArray.parseArray(jsonstr,Carbrand.class);
        for(Carbrand carbrand:list){
            System.out.println("brand:"+carbrand.getBrand()+",picurl:"+carbrand.getPicurl());
        }
    }
}
