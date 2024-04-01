import java.util.Scanner;

public class ABB {
    private static class Tree{
        public int dado;
        public Tree dir;
        public Tree esq;
    }
    public static Tree init(){
        return null;
    }

    private static Tree inserir(Tree p, int info){
        if(p==null){
            p = new Tree();
            p.dado = info;
            p.esq = null;
            p.dir = null;
        }else if(info<p.dado){
            p.esq = inserir(p.esq, info);
        }else{
            p.dir = inserir(p.dir, info);
        }
        return p;
    }

    private static Tree remover(Tree p, int info){
        if(p!=null) {
            if (info == p.dado) {
                if (p.esq == null && p.dir == null) {
                    return null;
                }
                if (p.esq == null) {
                    return p.dir;
                } else {
                    if (p.dir == null) {
                        return p.esq;
                    } else {
                        Tree aux, ref;
                        ref = p.dir;
                        aux = p.dir;
                        while (aux.esq != null) {
                            aux = aux.esq;
                        }
                        aux.esq = p.esq;
                        return ref;
                    }

                }
            }

            else {
                if (info < p.dado) {
                    p.esq = remover(p.esq, info);
                } else {
                    p.dir = remover(p.dir, info);
                }
            }

        }

        return p;
    }
    private static void mostra_em_ordem(Tree p){
        if(p!= null){
            mostra_em_ordem(p.esq);
            System.out.println(" " + p.dado);
            mostra_em_ordem(p.dir);
        }
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int op, item;
        Tree abb = init();

        do{
            System.out.println("0 - Sair");
            System.out.println("1 - Adicionar elemento na ABB");
            System.out.println("2 - Listar elementos na ABB");
            System.out.println("3 - Retirar elemento na ABB");
            op = entrada.nextInt();

            switch(op){
                case 0:
                    System.out.println("Adeus!!");
                    break;
                case 1:
                    System.out.println("Digite o valor do elemento");
                    item = entrada.nextInt();
                    abb = inserir(abb, item);
                    break;
                case 2:
                    System.out.println("Listando em ordem:");
                    mostra_em_ordem(abb);
                    break;
                case 3:
                    System.out.println("Qual elemento deseja retirar?");
                    item = entrada.nextInt();
                    abb = remover(abb, item);
                    break;
                default:
                    op = -1;
                    System.out.println("Opção inválida!");
            }
        }while(op != 0);
        entrada.close();
    }
}
