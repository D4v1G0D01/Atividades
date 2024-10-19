#include <iostream>
#include <string>
#include <fstream>
#include <stdexcept>
#define MAX 1000

using namespace std;


//COMEÇO CLASSE DATA
class Data {
private:
    int dia;
    int mes;
    int ano;

public:
    void setDia(int d) { dia = d; }
    void setMes(int m) { mes = m; }
    void setAno(int a) { ano = a; }

    int getDia() const { return dia; }
    int getMes() const { return mes; }
    int getAno() const { return ano; }

    bool dataValida() const {
        if (ano < 1 || mes < 1 || mes > 12 || dia < 1 || dia > 31)
            return false;
        return true;
    }

    void setData(int d, int m, int a) {
        setDia(d);
        setMes(m);
        setAno(a);
    }

    void lerData() {
        cout << "Digite o dia: ";
        cin >> dia;
        cout << "Digite o mês: ";
        cin >> mes;
        cout << "Digite o ano: ";
        cin >> ano;
    }

    void escreverData() const {
        cout << dia << "/" << mes << "/" << ano << endl;
    }

    bool verificarMes(int m) const {
        return mes == m;
    }

    friend ostream& operator<<(ostream& os, const Data& data);
    friend istream& operator>>(istream& is, Data& data);
};
//FIM DA CLASSE DATA



ostream& operator<<(ostream& os, const Data& data) {
    os << data.dia << " " << data.mes << " " << data.ano;
    return os;
}

istream& operator>>(istream& is, Data& data) {
    is >> data.dia >> data.mes >> data.ano;
    return is;
}


//COMEÇO CLASSE PESSOA
class Pessoa {
private:
    string nome;
    Data dataNascimento;

public:
    Pessoa() {}
    Pessoa(const string& n, const Data& d) : nome(n), dataNascimento(d) {}

    void setNome(const string& n) { nome = n; }
    string getNome() const { return nome; }

    Data getDataDeNascimento() const { return dataNascimento; }

    virtual void escrevaPessoa() const {
        cout << "Nome: " << nome << endl;
        cout << "Data de Nascimento: ";
        dataNascimento.escreverData();
    }

    void lerPessoa() {
        cout << "Digite o nome: ";
        cin.ignore();
        getline(cin, nome);
        dataNascimento.lerData();
    }

    friend ostream& operator<<(ostream& os, const Pessoa& pessoa);
    friend istream& operator>>(istream& is, Pessoa& pessoa);
};
//FIM DA CLASSE PESSOA


ostream& operator<<(ostream& os, const Pessoa& pessoa) {
    os << pessoa.nome << " " << pessoa.dataNascimento;
    return os;
}

istream& operator>>(istream& is, Pessoa& pessoa) {
    is >> pessoa.nome >> pessoa.dataNascimento;
    return is;
}

class Aluno : public Pessoa {
private:
    int matricula;

//COMEÇO CLASSE ALUNO
public:
 static int quantidade;
    Aluno() { quantidade++; }
    Aluno(const string& n, const Data& d, int m) : Pessoa(n, d), matricula(m) { quantidade++; }

    void setMatricula(int m) { matricula = m; }
    int getMatricula() const { return matricula; }

    void lerAluno() {
        lerPessoa();
        cout << "Digite a matrícula: ";
        cin >> matricula;
    }

    void escrevaPessoa() const override {
        Pessoa::escrevaPessoa();
        cout << "Matrícula: " << matricula << endl;
    }

    static int getQuantidade() { return quantidade; }
    static void decrementaQuantidade() { quantidade--; }

    friend ostream& operator<<(ostream& os, const Aluno& aluno);
    friend istream& operator>>(istream& is, Aluno& aluno);

virtual ~Aluno() {}
};
//FIM DA CLASSE ALUNO



ostream& operator<<(ostream& os, const Aluno& aluno) {
    os << static_cast<const Pessoa&>(aluno) << " " << aluno.matricula;
    return os;
}

istream& operator>>(istream& is, Aluno& aluno) {
    is >> static_cast<Pessoa&>(aluno) >> aluno.matricula;
    return is;
}

int Aluno::quantidade = 0;


//COMEÇO CLASSE PROFESSOR
class Professor : public Pessoa {
private:
    string titulacao;


public:
    static int quantidade;
    Professor() { quantidade++; }
    Professor(const string& n, const Data& d, const string& t) : Pessoa(n, d), titulacao(t) { quantidade++; }

    void setTitulacao(const string& t) { titulacao = t; }
    string getTitulacao() const { return titulacao; }

    void lerProfessor() {
        lerPessoa();
        cout << "Digite a titulação (Especialista, Mestre ou Doutor): ";
        cin.ignore();
        getline(cin, titulacao);
    }

    void escrevaPessoa() const override {
        Pessoa::escrevaPessoa();
        cout << "Titulação: " << titulacao << endl;
    }

    static int getQuantidade() { return quantidade; }

    friend ostream& operator<<(ostream& os, const Professor& professor);
    friend istream& operator>>(istream& is, Professor& professor);

virtual ~Professor() {}
};
//FIM DA CLASSE PROFESSOR


ostream& operator<<(ostream& os, const Professor& professor) {
    os << static_cast<const Pessoa&>(professor) << " " << professor.titulacao;
    return os;
}

istream& operator>>(istream& is, Professor& professor) {
    is >> static_cast<Pessoa&>(professor) >> professor.titulacao;
    return is;
}

int Professor::quantidade = 0;

void cadastrarAluno(Aluno* alunos[]) {
    try {
        if (Aluno::getQuantidade() >= MAX) {
            cout << "Cadastro cheio!" << endl;
            return;
        } else {
            Aluno* novoAluno = new Aluno();
            novoAluno->lerAluno();
            alunos[Aluno::getQuantidade() - 1] = novoAluno;
        }
    } catch (const bad_alloc& e) {
        cout << "Erro de alocação de memória: " << e.what() << endl;
    }
}

void cadastrarProfessor(Professor* professores[]) {
    try {
        if (Professor::getQuantidade() >= MAX) {
            cout << "Cadastro cheio!" << endl;
            return;
        } else {
            Professor* novoProfessor = new Professor();
            novoProfessor->lerProfessor();
            professores[Professor::getQuantidade() - 1] = novoProfessor;
        }
    } catch (const bad_alloc& e) {
        cout << "Erro de alocação de memória: " << e.what() << endl;
    }
}

void listarAlunos(Aluno* alunos[]) {
    for (int i = 0; i < Aluno::getQuantidade(); i++) {
        cout << "Aluno " << i + 1 << ":\n";
        alunos[i]->escrevaPessoa();
        cout << endl;
    }
}

void listarProfessores(Professor* professores[]) {
    for (int i = 0; i < Professor::getQuantidade(); i++) {
        cout << "Professor " << i + 1 << ":\n";
        professores[i]->escrevaPessoa();
        cout << endl;
    }
}

void listarAniversariantesDoMes(Pessoa* pessoas[], int quantidade, int mes) {
    for (int i = 0; i < quantidade; i++) {
        if (pessoas[i]->getDataDeNascimento().verificarMes(mes)) {
            pessoas[i]->escrevaPessoa();
            cout << endl;
        }
    }
}

void salvarDados(Aluno* alunos[], Professor* professores[]) {
    ofstream outFile("dados.txt");
    if (outFile.is_open()) {
        outFile << Aluno::getQuantidade() << " " << Professor::getQuantidade() << endl;
        for (int i = 0; i < Aluno::getQuantidade(); i++) {
            outFile << *alunos[i] << endl;
        }
        for (int i = 0; i < Professor::getQuantidade(); i++) {
            outFile << *professores[i] << endl;
        }
        outFile.close();
    }
}

void carregarDados(Aluno* alunos[], Professor* professores[]) {
    ifstream inFile("dados.txt");
    if (inFile.is_open()) {
        int qtdAlunos, qtdProfessores;
        inFile >> qtdAlunos >> qtdProfessores;
        Aluno::quantidade = qtdAlunos;
        Professor::quantidade = qtdProfessores;

        for (int i = 0; i < qtdAlunos; i++) {
            Aluno* aluno = new Aluno();
            inFile >> *aluno;
            alunos[i] = aluno;
        }
        for (int i = 0; i < qtdProfessores; i++) {
            Professor* professor = new Professor();
            inFile >> *professor;
            professores[i] = professor;
        }
        inFile.close();
    }
}

int menu() {
    int opcao;
    do {
        cout << "Menu de Opções:\n";
        cout << "0 - Sair\n";
        cout << "1 - Cadastrar (A/P)\n";
        cout << "2 - Listar (A/P)\n";
        cout << "3 - Alterar (A/P)\n";
        cout << "4 - Excluir (A/P)\n";
        cout << "5 - Aniversariantes do mês\n";
        cout << "Escolha uma opção: ";
        cin >> opcao;
    } while (opcao < 0 || opcao > 5);
    return opcao;
}

int submenu() {
    int opcao;
    do {
        cout << "0 - Retornar\n";
        cout << "1 - Aluno\n";
        cout << "2 - Professor\n";
        cout << "Escolha uma opção: ";
        cin >> opcao;
    } while (opcao < 0 || opcao > 2);
    return opcao;
}


void alterarAluno(Aluno* alunos[], int indice) {
    if (indice >= 0 && indice < Aluno::getQuantidade()) {
        cout << "Alterando dados do Aluno " << indice + 1 << ":\n";
        alunos[indice]->lerAluno();
    } else {
        cout << "Índice inválido!\n";
    }
}

void alterarProfessor(Professor* professores[], int indice) {
    if (indice >= 0 && indice < Professor::getQuantidade()) {
        cout << "Alterando dados do Professor " << indice + 1 << ":\n";
        professores[indice]->lerProfessor();
    } else {
        cout << "Índice inválido!\n";
    }
}

void excluirAluno(Aluno* alunos[], int& quantidade, int indice) {
    if (indice >= 0 && indice < quantidade) {
        delete alunos[indice];
        for (int i = indice; i < quantidade - 1; i++) {
            alunos[i] = alunos[i + 1];
        }
        quantidade--;
        Aluno::quantidade--;
    } else {
        cout << "Índice inválido!\n";
    }
}

void excluirProfessor(Professor* professores[], int& quantidade, int indice) {
    if (indice >= 0 && indice < quantidade) {
        delete professores[indice];
        for (int i = indice; i < quantidade - 1; i++) {
            professores[i] = professores[i + 1];
        }
        quantidade--;
        Professor::quantidade--;
    } else {
        cout << "Índice inválido!\n";
    }
}

int main() {
    Aluno* alunos[MAX] = {nullptr};
    Professor* professores[MAX] = {nullptr};
    Pessoa* pessoas[MAX * 2] = {nullptr}; // Arranjo de ponteiro para pessoas que suportará todos os alunos e professores

    carregarDados(alunos, professores);

    int opcao;
    do {
        opcao = menu();
        int subopcao;
        switch (opcao) {
            case 1:
                subopcao = submenu();
                if (subopcao == 1) {
                    cadastrarAluno(alunos);
                } else if (subopcao == 2) {
                    cadastrarProfessor(professores);
                }
                break;
            case 2:
                subopcao = submenu();
                if (subopcao == 1) {
                    listarAlunos(alunos);
                } else if (subopcao == 2) {
                    listarProfessores(professores);
                }
                break;
            case 3:
                subopcao = submenu();
                if (subopcao == 1) {
                    int indice;
                    listarAlunos(alunos);
                    cout << "Digite o índice do aluno a alterar: ";
                    cin >> indice;
                    alterarAluno(alunos, indice - 1);
                } else if (subopcao == 2) {
                    int indice;
                    listarProfessores(professores);
                    cout << "Digite o índice do professor a alterar: ";
                    cin >> indice;
                    alterarProfessor(professores, indice - 1);
                }
                break;
            case 4:
            subopcao = submenu();
            if (subopcao == 1) {
                int indice;
                listarAlunos(alunos);
                cout << "Digite o índice do aluno a excluir: ";
                cin >> indice;
                int quantidadeAlunos = Aluno::getQuantidade();
                excluirAluno(alunos, quantidadeAlunos, indice - 1);
            } else if (subopcao == 2) {
                int indice;
                listarProfessores(professores);
                cout << "Digite o índice do professor a excluir: ";
                cin >> indice;
                int quantidadeProfessores = Professor::getQuantidade();
                excluirProfessor(professores, quantidadeProfessores, indice - 1);
            }
            break;
            case 5:
                int mes;
                cout << "Digite o mês: ";
                cin >> mes;
                int quantidade = 0;
                for (int i = 0; i < Aluno::getQuantidade(); i++) {
                    pessoas[quantidade++] = alunos[i];
                }
                for (int i = 0; i < Professor::getQuantidade(); i++) {
                    pessoas[quantidade++] = professores[i];
                }
                listarAniversariantesDoMes(pessoas, quantidade, mes);
                break;
        }
    } while (opcao != 0);

    salvarDados(alunos, professores);

    for (int i = 0; i < Aluno::getQuantidade(); i++) {
        delete alunos[i];
    }
    for (int i = 0; i < Professor::getQuantidade(); i++) {
        delete professores[i];
    }

    return 0;
}
