package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;
@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        if (account == null) {
            return false;
        }
        accounts.put(account.id(), account);
        return accounts.containsKey(account.id());
    }

    public synchronized boolean update(Account account) {
        if (account == null) {
            return false;
        }
        Account old = accounts.get(account.id());
        return accounts.replace(old.id(), old, account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account fromOld = accounts.get(fromId);
        Account toOld = accounts.get(toId);
        if (!(accounts.get(fromOld.id()).amount() >= amount)) {
            return false;
        }
        int fromAmount = fromOld.amount() - amount;
        int toAmount = toOld.amount() + amount;
        Account fromNew = new Account(fromOld.id(), fromAmount);
        Account toNew = new Account(toOld.id(), toAmount);
        update(fromNew);
        update(toNew);
        return accounts.get(fromId).amount() == fromNew.amount()
                && accounts.get(toId).amount() == toNew.amount();
    }

}
