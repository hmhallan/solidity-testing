pragma solidity >=0.4.22 <0.6.0;

contract Counter {
    
    uint256 counter;
    
    address owner = msg.sender; //dono do contrato é o criador


    //construtor
    constructor() public {
        counter = 5;
    }
    
    //setter
    function setCounter( uint256 _counter ) public {
        counter = _counter;
    }
    
    //view significa que nao tem transacao, nao precisa minerar (nao usa gas para executar)
    function getCounter() public view returns (uint256) {
        return counter;
    } 

}