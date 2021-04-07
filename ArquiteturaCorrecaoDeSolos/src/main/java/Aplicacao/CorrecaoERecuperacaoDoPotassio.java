
package Aplicacao;


public class CorrecaoERecuperacaoDoPotassio {
    private double quantDePotassioNoSolo;
    private double quantDeCalcioNoSolo;
    private double quantDeMagnseioNoSolo;
    private double quantDeAluminioHidrogenioNoSolo;
    private double participacaoPercentualDesejadaDoPotassioNaCTC;
    
    public CorrecaoERecuperacaoDoPotassio(
        double quantDePotassioNoSolo, 
        double quantDeCalcioNoSolo,
        double quantDeMagnseioNoSolo,
        double quantDeAluminioHidrogenioNoSolo,
        double participacaoPercentualDesejadaDoPotassioNaCTC){
            
           this.quantDeAluminioHidrogenioNoSolo = quantDeAluminioHidrogenioNoSolo;
           this.quantDeCalcioNoSolo = quantDeCalcioNoSolo;
           this.quantDeMagnseioNoSolo = quantDeMagnseioNoSolo;
           this.quantDePotassioNoSolo = quantDePotassioNoSolo;
           this.participacaoPercentualDesejadaDoPotassioNaCTC = participacaoPercentualDesejadaDoPotassioNaCTC;
    }
    
    EquilibrioECorrecaoCTC ECCTC = new EquilibrioECorrecaoCTC(0.15,5.76,1.63,5.35,30.7);
    
    double participacaoPercentualAtualDoPotassioNaCTCSolo(){
        if(this.quantDePotassioNoSolo > 0 && ECCTC.calculoCTCCmol() > 0){
            return this.quantDePotassioNoSolo / ECCTC.calculoCTCCmol()*100;
        }
        return 0.0;
    }
    
    double participacaoPercentualPotassioNaCTCPosCorrecao(){
        if(this.participacaoPercentualDesejadaDoPotassioNaCTC > 0.001){
            return this.participacaoPercentualDesejadaDoPotassioNaCTC;
        }
        return 0.0;
    }
    
    double participacaoPercentualIdealDoPotassioNaCTC(int texturaDoSolo){
        if(texturaDoSolo == 1 || texturaDoSolo == 2){
            return 3.0;
        }
        return 0.0;
    }
    
    double calculoDaNescessidadeDePotassioAdiconar(){
        if (this.quantDePotassioNoSolo > 0 && 
            participacaoPercentualAtualDoPotassioNaCTCSolo() > this.quantDePotassioNoSolo){
            
            return this.quantDePotassioNoSolo * this.participacaoPercentualDesejadaDoPotassioNaCTC / 
                    participacaoPercentualAtualDoPotassioNaCTCSolo() - this.quantDePotassioNoSolo;
        } 
        return 0.0;
    }
    
    double calculoDaQuantidadeDaFonteAplicar(int fonteDePotassioUsar){
        if (calculoDaNescessidadeDePotassioAdiconar() > 0.001){
            switch (fonteDePotassioUsar){
                case 1:
                    return (calculoDaNescessidadeDePotassioAdiconar()* 39.1 * 10 * 2 * 1.2 * 100/85 * 100/58.0);
                case 2:
                    return (calculoDaNescessidadeDePotassioAdiconar()* 39.1* 10 * 2 * 1.2 * 100/85 * 100/52.0);
                case 3:
                    return (calculoDaNescessidadeDePotassioAdiconar()* 39.1 * 10 * 2 * 1.2 * 100/85 * 100/22.0);
                case 4:
                    return (calculoDaNescessidadeDePotassioAdiconar()* 39.1 * 10 * 2 * 1.2 * 100/85 * 100/44.0);
            }      
        }
        return 0.0;
    }
    
    double calculoDoCustoDaFonteUtilizarPorHectare(
            int fonteDePotassioUsar, 
            double valorPorTonelada,
            double calculoDaQuantidadeDaFonteAplicar,
            int texturaSolo){
        
        if(fonteDePotassioUsar >= 1 && fonteDePotassioUsar <= 3){
            return (calculoDaQuantidadeDaFonteAplicar/1000) * valorPorTonelada;
        }
        if(fonteDePotassioUsar == 4){
            switch (texturaSolo) {
                case 1:
                    return 0.7*calculoDaQuantidadeDaFonteAplicar/1000;
                case 2:
                    return 0.5*calculoDaQuantidadeDaFonteAplicar/1000;
                default:
                    return 0.0;
            }
        }
        return 0.0;

    }
}
