package Persitent.DaoInterfaces;

import Domain.OwnerAgreement;

public interface IOwnerAgreementDao {
    boolean add(OwnerAgreement value);
    boolean update(OwnerAgreement info);
    boolean remove( int id);
    OwnerAgreement find(int id);
}
