package com.richinfo.util;

import com.alibaba.fastjson.JSONObject;
import com.richinfo.annotation.po.*;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    public static void main(String[] args){
        Record  record = new Record();
        record.setCasename("案件名称");
        record.setFilename("100002.doc");
        record.setRecordname("丁北玉吸毒贩毒案");
        record.setType("吸毒贩毒");
        record.setStarttime("2016年1月29日13时13分");
        record.setEndtime("2016年1月29日14时01分");
        record.setPlace("日照市公安局执法办案功能区");
        record.setAsker("李警官");
        record.setRecorder("刘警官");

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

        Contact contact0= new Contact();
        contact0.setName("丁北玉");
        contact0.setType("手机号");
        contact0.setNo("13964240123");
        List<Contact> contacts0 = new ArrayList<Contact>();
        contacts0.add(contact0);
        person.setContacts(contacts0);

        Preconviction preconviction = new Preconviction();
        preconviction.setStarttime("2014年10月20日");
        preconviction.setReason("判刑原因贩毒");
        preconviction.setTerm("刑期3年");
        preconviction.setPlace("判刑地点：日照市中级人民法院");

        person.setPreconviction(preconviction);
        record.setPerson(person);

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
        relation3.setTime("十五年前");
        relation3.setWay("上学");

        List<Relation> relations3 = new ArrayList<Relation>();
        relations3.add(relation3);
        person1.setRelations(relations3);

        List<Relation> relations1 = new ArrayList<Relation>();
        Relation relation1 =new Relation();
        relation1.setName("刘XXX");
        relation1.setRelation("同事");
        relation1.setTime("十年前");
        relation1.setWay("同在一个工厂打工");
        relations1.add(relation1);

        Relation relation2 =new Relation();
        relation2.setName("丁凯");
        relation2.setRelation("同吸");
        relation2.setTime("五年前");
        relation2.setWay("朋友介绍");
        relations1.add(relation2);

        person.setRelations(relations1);

        Event event1 = new Event();
        event1.setType("带货");
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

        Event event2 = new Event();
        event2.setType("吸毒贩毒");
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
        event2.setTime("2015年12月5日 上午");
        event2.setPlace("日照市XXX宾馆");
        List<Goods> goods = new ArrayList<Goods>();
        Goods goods1 = new Goods();
        goods1.setName("冰毒");
        goods1.setWeight("0.8克");
        goods1.setMoney("400元");
        goods.add(goods1);
        event2.setGoods(goods);

        List<Car> cars= new ArrayList<Car>();
        Car car =new Car();
        car.setBrand("帕萨特");
        car.setColor("黑色");
        car.setNo("鲁L12320");
        car.setType("轿车");

        List<CarPerson> carpersons = new ArrayList<CarPerson>();

        CarPerson carPerson = new CarPerson();
        carPerson.setName("丁北玉");
        carPerson.setType("乘车");
        carpersons.add(carPerson);
        CarPerson carPerson1 = new CarPerson();
        carPerson1.setName("孙凯");
        carPerson1.setType("车主");
        carpersons.add(carPerson1);

        car.setPersons(carpersons);
        cars.add(car);
        event2.setCars(cars);

        event2.setTag("本地人员涉案");

        List<Event> events =new ArrayList<Event>();
        events.add(event1);
        events.add(event2);


        record.setEvents(events);

        String jsonString = JSONObject.toJSONString(record);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);

    }
}
