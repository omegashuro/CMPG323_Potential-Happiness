package za.ac.nwu.ac.logic.flow.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.dto.MemberAccountDto;
import za.ac.nwu.ac.logic.flow.ModifyMemberAccountFlow;
import za.ac.nwu.ac.translator.MemberAccountTranslator;

import javax.transaction.Transactional;

@Transactional
@Component("modifyMemberAccountFlowName")
public class ModifyMemberAccountFlowImpl implements ModifyMemberAccountFlow {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModifyMemberAccountFlowImpl.class);
    private final MemberAccountTranslator translator;

    public ModifyMemberAccountFlowImpl(MemberAccountTranslator translator) {
        this.translator = translator;
    }

    @Transactional
    @Override
    public MemberAccountDto subtractMiles(Integer amount, Long memberID, Long accountTypeID) {

        if(0<amount){
            amount=amount * -1;
        }
        LOGGER.info("The UserAccount to Update has input values: " + "\n\tamount = {}" +
                "\n\tmemberID = {}" + "\n\taccountTypeID = {}", amount,memberID,  accountTypeID);

        Integer oldBal= 0;
        Integer newBal= 0;
        oldBal= translator.getMember(memberID,accountTypeID).getBalance();
        if(amount + oldBal >=0){
            LOGGER.info("Transaction is Valid - Subtracting less than current AccountValue");
            newBal = amount + oldBal;
            MemberAccountDto result =translator.updateMemberAccount(newBal, memberID, accountTypeID);
            LOGGER.info("The UserAccount was updated and has return object {}",result);
            return result;
        }else{
            LOGGER.info("");
            throw new RuntimeException("Unable to Update the database");
        }
    }
    
    @Transactional
    @Override
    public MemberAccountDto addMiles(Integer amount, Long memberID, Long accountTypeID) {


        LOGGER.info("The UserAccount to Update has input values: " + "\n\tamount = {}" + "\n\tmemberID = {}" +
                "\n\taccountTypeID = {}", amount,memberID,  accountTypeID);
        Integer oldBal= 0;
        Integer newBal= 0;
        oldBal= translator.getMember(memberID,accountTypeID).getBalance();
        newBal = amount + oldBal;
        MemberAccountDto result =translator.updateMemberAccount(newBal, memberID, accountTypeID);
        LOGGER.info("The UserAccount was updated and has return object {}",result);
        return result;

    }

}
