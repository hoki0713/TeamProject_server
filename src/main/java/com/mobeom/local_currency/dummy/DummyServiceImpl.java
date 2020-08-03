package com.mobeom.local_currency.dummy;

import com.mobeom.local_currency.sales.Sales;
import com.mobeom.local_currency.sales.SalesRepository;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
interface DummyService {
    List<User> createRandomUser();

    List<Sales> createRandomPurchaseHistory();
}

@Service
public class DummyServiceImpl implements DummyService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    LocalCurrencyVoucherRepository localCurrencyVoucherRepository;

    @Override
    public List<User> createRandomUser() {
        List<User> userList = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            User user = new User();
            user.setUserId(RandomUserGenerator.generateRandomId()+RandomUserGenerator.generateRandomNo2());
            user.setPassword(RandomUserGenerator.generateRandomPw()+RandomUserGenerator.generateRandomPwNum());
            user.setName(RandomUserGenerator.generateRandomName());
            user.setBirthDate(RandomUserGenerator.generateRandomBirthDate());
            user.setGender(RandomUserGenerator.generateRandomGender());
            user.setEmail(RandomUserGenerator.generateRandomEmailId()+"@"+RandomUserGenerator.generateRandomEmail()+
                    RandomUserGenerator.generateRandomEmailEnd());
            user.setJoinDate(RandomUserGenerator.generateRandomJoinDate());
            user.setAdminKey(0);
            user.setDefaultAddr(RandomUserGenerator.generateRandomAddress());
            userList.add(user);
        }
        return userRepository.saveAll(userList);
    }

    @Override
    public List<Sales> createRandomPurchaseHistory() {
        List<Sales> purchaseHistoryList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for(int i = 0; i < 100000; i++) {
            Sales purchaseHistory = new Sales();
            purchaseHistory.setSalesDate(RandomPurchaseHistoryGenerator.generateRandomDate());
            purchaseHistory.setGiftYn(RandomPurchaseHistoryGenerator.generateRandomBoolean());
            if(purchaseHistory.isGiftYn()) {
                purchaseHistory.setCurrencyState("사용완료");
            } else {
                purchaseHistory.setCurrencyState(RandomPurchaseHistoryGenerator.generateRandomCurrencyState());
            }
            if(purchaseHistory.getCurrencyState().equals("사용완료")) {
                purchaseHistory.setUseDate(purchaseHistory.getSalesDate().plusDays((int)(Math.random()*31)));
            } else {
                purchaseHistory.setUseDate(null);
            }
            if(purchaseHistory.getCurrencyState().equals("취소완료")) {
                purchaseHistory.setCancelDate(purchaseHistory.getSalesDate().plusDays((int)(Math.random()*7)));
            } else {
                purchaseHistory.setCancelDate(null);
            }
            purchaseHistory.setPaymentName(RandomPurchaseHistoryGenerator.generateRandomPaymentCompany());
            purchaseHistory.setUser(userList.get((int)(Math.random()*userList.size()-1)));
            purchaseHistory.setUnitPrice(RandomPurchaseHistoryGenerator.generateRandomVoucherPrice());

            String address = purchaseHistory.getUser().getDefaultAddr();

            List<LocalCurrencyVoucher> selectedLocalCurrencyList =
                    localCurrencyVoucherRepository.findAllByDefaultAddr(address);

            purchaseHistory.setLocalCurrencyVoucher(
                    selectedLocalCurrencyList.get((int)(Math.random()*2)));

            purchaseHistoryList.add(purchaseHistory);

        }
        return salesRepository.saveAll(purchaseHistoryList);
    }
}
