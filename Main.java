import java.util.Scanner;

public class Main {
    private static class Tree{
        public int dado;
        public Tree dir;
        public Tree esq;
        public int h_esq, h_dir;
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
            if(p.esq.h_dir > p.esq.h_esq)
                p.h_esq = p.esq.h_dir+1;
            else
                p.h_esq = p.esq.h_esq+1;
            p = balanceamento(p);

        }else{
            p.dir = inserir(p.dir, info);
            if(p.dir.h_dir > p.dir.h_esq){
                p.h_dir = p.dir.h_dir +1;
            }else{
                p.h_dir = p.dir.h_esq+1;
            }
            p = balanceamento(p);
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

    public static Tree atualiza_alturas(Tree p){
        if(p!= null){
            p.esq = atualiza_alturas(p.esq);
            if(p.esq == null){
                p.h_esq = 0;
            }else if(p.esq.h_esq > p.esq.h_dir){
                p.h_esq = p.esq.h_esq+1;
            }else{
                p.h_esq = p.esq.h_dir+1;
            }
            p.dir = atualiza_alturas(p.dir);
            if(p.dir == null){
                p.h_dir=0;
            }else if(p.dir.h_esq>p.dir.h_dir){
                p.h_dir = p.dir.h_esq+1;
            }else{
                p.h_dir = p.dir.h_dir+1;
            }
            p = balanceamento(p);
        }
        return p;
    }

    public static Tree rotacao_direita(Tree p){
    Tree q, temp;
    q = p.esq;
    temp = q.dir;
    q.dir = p;
    p.esq = temp;
    if(temp == null){
        p.h_esq = 0;
    }else if(temp.h_dir > temp.h_esq){
            p.h_esq = temp.h_dir+1;
    }else{
        p.h_esq = temp.h_esq+1;
    }
    if(p.h_dir > p.h_esq){
        q.h_dir = p.h_dir+1;
    }else{
        q.h_dir = p.h_esq +1;
    }

    return q;
    }

    public static Tree rotacao_esquerda(Tree p){
        Tree q, temp;
        q = p.dir;
        temp = q.esq;
        q.esq = p;
        p.dir = temp;
        if(temp == null){
            p.h_dir = 0;
        }else if(temp.h_dir > temp.h_esq){
            p.h_dir = temp.h_dir+1;
        }else{
            p.h_dir = temp.h_esq+1;
        }

        if(p.h_dir > p.h_esq){
            q.h_esq = p.h_dir+1;
        }else{
            q.h_esq = p.h_esq +1;
        }


        return q;
    }

    public static Tree balanceamento (Tree p){
        int FB = p.h_dir - p.h_esq;
        if(FB>1){
            int FB_filho_dir = p.dir.h_dir - p.dir.h_esq;
            if(FB_filho_dir >=0){
                p = rotacao_esquerda(p);
            }else{
                p.dir = rotacao_direita(p.dir);
                p = rotacao_esquerda(p);
            }
        }
        else{
            if(FB < -1){
                int FB_filho_esq = p.esq.h_dir  - p.esq.h_esq;
                if(FB_filho_esq <= 0){
                    p = rotacao_direita(p);
                }else{
                    p.esq = rotacao_esquerda(p.esq);
                    p = rotacao_direita(p);
                }
            }
        }
        return p;
    }

    public static boolean consulta (Tree p, int info ){
        boolean exists;
        if(info == p.dado){
           return true;
        }
        if(info > p.dado){
            if(p.dir == null){
               return false;
            }else {
               exists = consulta(p.dir, info);
            }
        }else{
            if(p.esq == null){
               return false;
            }else {
              exists =  consulta(p.esq, info);
            }
            }

        return exists;
    }

    private static void mostra_em_ordem(Tree p){
        if(p!= null){
            mostra_em_ordem(p.esq);
            System.out.println(" " + p.dado + " h_dir "+ p.h_dir + " h_esq "+p.h_esq);
            mostra_em_ordem(p.dir);
        }
    }
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Tree raiz = init();
        int op, info;



        do{
            System.out.println("0 - Encerrar programa");
            System.out.println("1 - Inserir 1 elemento na árvore");
            System.out.println("2 - Remover 1 elemento na árvore");
            System.out.println("3 - Listar elementos da árvore");
            op = entrada.nextInt();
            switch (op){
                case 0:
                    System.out.println("Até mais!");
                    break;
                case 1:
                    System.out.println("Digite o valor do elemento: ");
                    info = entrada.nextInt();
                    raiz = inserir(raiz, info);
                    break;
                case 2:
                    System.out.println("Digite o valor do nó a ser removido: ");
                    info = entrada.nextInt();
                    if(!consulta(raiz, info)){
                        System.out.println("Não encontrado na arvore");
                    }else {
                        raiz = remover(raiz, info);
                        raiz = atualiza_alturas(raiz);
                    }
                    break;
                case 3:
                    System.out.println("Apresentação da árvore em ordem: ");
                    mostra_em_ordem(raiz);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }while (op!=0);


        entrada.close();
    }
}