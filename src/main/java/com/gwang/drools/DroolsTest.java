package com.gwang.drools;

import com.google.common.collect.Lists;
import com.gwang.drools.domain.DistanceRule;
import com.gwang.drools.domain.DistanceRulePair;
import com.gwang.drools.domain.FeeContext;
import com.gwang.drools.domain.ItemCity;
import org.drools.core.marshalling.impl.ProtobufMessages;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gangwang on 2019/7/2.
 */
public class DroolsTest {

    public static void main(String[] args) {
        try {
            KieServices kieServices = KieServices.Factory.get();
            KieContainer kieContainer = kieServices.newKieClasspathContainer();

            KieSession kieSession = kieContainer.newKieSession("ksession-rules");

//            // load up the knowledge base
//            ProtobufMessages.KnowledgeBase kbase = readKnowledgeBase();
//            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

            ItemCity item1 = new ItemCity();
            item1.setPurchaseCity(ItemCity.City.PUNE);
            item1.setTypeofItem(ItemCity.Type.MEDICINES);
            item1.setSellPrice(new BigDecimal(10));
            kieSession.insert(item1);

            ItemCity item2 = new ItemCity();
            item2.setPurchaseCity(ItemCity.City.PUNE);
            item2.setTypeofItem(ItemCity.Type.GROCERIES);
            item2.setSellPrice(new BigDecimal(10));
            kieSession.insert(item2);

            ItemCity item3 = new ItemCity();
            item3.setPurchaseCity(ItemCity.City.NAGPUR);
            item3.setTypeofItem(ItemCity.Type.MEDICINES);
            item3.setSellPrice(new BigDecimal(10));
            kieSession.insert(item3);

            ItemCity item4 = new ItemCity();
            item4.setPurchaseCity(ItemCity.City.NAGPUR);
            item4.setTypeofItem(ItemCity.Type.GROCERIES);
            item4.setSellPrice(new BigDecimal(10));
            kieSession.insert(item4);

            FeeContext feeContext = new FeeContext();
            feeContext.setDistance(3100);
            DistanceRule distanceRule = new DistanceRule();
            distanceRule.setDefaultDistance(3000);
            List<DistanceRulePair> distanceRulePairs = Lists.newArrayList();
            distanceRulePairs.add(new DistanceRulePair(0, 1000, new BigDecimal(1)));
            distanceRulePairs.add(new DistanceRulePair(1000, 3000, new BigDecimal(2)));
            distanceRulePairs.add(new DistanceRulePair(3000, 6000, new BigDecimal(3)));
            distanceRule.setRulePairs(distanceRulePairs);
            kieSession.insert(feeContext);
            kieSession.insert(distanceRule);

            kieSession.fireAllRules();

            System.out.println(item1.getPurchaseCity().toString() + " "
                    + item1.getLocalTax().intValue());

            System.out.println(item2.getPurchaseCity().toString() + " "
                    + item2.getLocalTax().intValue());

            System.out.println(item3.getPurchaseCity().toString() + " "
                    + item3.getLocalTax().intValue());

            System.out.println(item4.getPurchaseCity().toString() + " "
                    + item4.getLocalTax().intValue());

            System.out.println("Distance fee:" + feeContext.getDistanceFee().intValue());

            kieSession.destroy();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

//    private static ProtobufMessages.KnowledgeBase readKnowledgeBase() throws Exception {
//
//        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//
//        kbuilder.add(ResourceFactory.newClassPathResource("Pune.drl"), ResourceType.DRL);
//        kbuilder.add(ResourceFactory.newClassPathResource("Nagpur.drl"), ResourceType.DRL);
//
//        KnowledgeBuilderErrors errors = kbuilder.getErrors();
//
//        if (errors.size() > 0) {
//            for (KnowledgeBuilderError error: errors) {
//                System.err.println(error);
//            }
//            throw new IllegalArgumentException("Could not parse knowledge.");
//        }
//
//        ProtobufMessages.KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
//        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
//
//        return kbase;
//    }
}
