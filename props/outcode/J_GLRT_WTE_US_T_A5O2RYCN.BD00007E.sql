/***************************************
	Query Passthrough su TD
	Esempio USER CODE:
	execute (
				replace view &tdStagedb..&table_target. as
				select geography_dim.*
				from &tdStagedb..geography_dim geography_dim
			
		) by teradata;
***************************************/

%macro select;
/************************************
			BEGIN USER CODE 
************************************/
execute(replace view &tdStagedb..&table_target. as
				(SELECT DISTINCT  
              (case 
                      when((reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_UES.GLRT_WTE_US s) )then 'Current Date'
                      else 'Other Dates'
               end ) as "Current_Date" ,
         REFERENCE_DT,cast(SK_ID_NOCFL as VARCHAR(20)) SK_ID_NOCFL, SCENARIO_CD, SUBSCENARIO_CD,
        FREQ_CD, ORIGINAL_REFERENCE_DT, PROVIDER_CD, 
         FLOW_TYPE_CD, GLRT_SOURCE_CD, TEMPLATE_CD, TEMPLATE_ROW_CD,
        TEMPLATE_ROW_DESC, LEGAL_ENTITY_CD, INTRAGROUP_COUNTERPARTY_IND,
        INTRAGROUP_COUNTERPARTY_CD, FINREP_CPARTY_TYPE_CD, PERIMETER_J_FLAG,
        PERIMETER_C_FLAG, US_INDIVIDUAL_LE_CD, US_CONSOLIDATED_LE_CD,
        t1.COUNTRY_CD, COUNTRY_DESC, ISIN_NUM, BALANCE_SHEET_ITEM_CD, REPORTING_CURRENCY_CD,
        CURRENCY_CD, B3_COUNTERPARTY_TYPE_CD, ASSET_CLASS_CD, COLLATERAL_TYPE_CD,
        CONTRACT_TYPE_CD, GUARANTEE_TYPE_CD, WTE_INSTRUMENT_TYPE_CD,
        ORIGINAL_MATURITY_CD, OPERATION_TYPE_CD, WTE_SIGN,  RETAIL_IND,
        SECURITY_TYPE_CD,
        SPEC_CREDIT_TYPE_CD, INTRAGROUP_COUNTRY_CD, INTRAGROUP_COUNTRY_DESC,
        t2.BRANCH_DESC,
        t2.CATEGORY,
        t2.ASSIGNED_COUNTRY,
        t2.LIQUIDITY_REFERENCE_BANK,
        MIN(ACTUAL_CARRYING_VALUE) AS ACTUAL_CARRYING_VALUE, 
        SUM(ACTUAL_NOMINAL_VALUE) AS ACTUAL_NOMINAL_VALUE,
        SUM(CASE WHEN MATURITY_CD='1M' THEN RUNOFF_VALUE ELSE 0 END ) AS M1,
        SUM(CASE WHEN MATURITY_CD='2M' THEN RUNOFF_VALUE ELSE 0 END ) AS M2,
        SUM(CASE WHEN MATURITY_CD='3M' THEN RUNOFF_VALUE ELSE 0 END ) AS M3,
        SUM(CASE WHEN MATURITY_CD='4M' THEN RUNOFF_VALUE ELSE 0 END ) AS M4,
        SUM(CASE WHEN MATURITY_CD='5M' THEN RUNOFF_VALUE ELSE 0 END ) AS M5,        
        SUM(CASE WHEN MATURITY_CD='6M' THEN RUNOFF_VALUE ELSE 0 END ) AS M6,        
        SUM(CASE WHEN MATURITY_CD='7M' THEN RUNOFF_VALUE ELSE 0 END ) AS M7,        
        SUM(CASE WHEN MATURITY_CD='8M' THEN RUNOFF_VALUE ELSE 0 END ) AS M8,        
        SUM(CASE WHEN MATURITY_CD='9M' THEN RUNOFF_VALUE ELSE 0 END ) AS M9,
        SUM(CASE WHEN MATURITY_CD='10M' THEN RUNOFF_VALUE ELSE 0 END ) AS M10,
        SUM(CASE WHEN MATURITY_CD='11M' THEN RUNOFF_VALUE ELSE 0 END ) AS M11,
        SUM(CASE WHEN MATURITY_CD='12M' THEN RUNOFF_VALUE ELSE 0 END ) AS M12,
        SUM(CASE WHEN MATURITY_CD='13M' THEN RUNOFF_VALUE ELSE 0 END ) AS M13,
        SUM(CASE WHEN MATURITY_CD='14M' THEN RUNOFF_VALUE ELSE 0 END ) AS M14,
        SUM(CASE WHEN MATURITY_CD='15M' THEN RUNOFF_VALUE ELSE 0 END ) AS M15,
        SUM(CASE WHEN MATURITY_CD='16M' THEN RUNOFF_VALUE ELSE 0 END ) AS M16,
        SUM(CASE WHEN MATURITY_CD='17M' THEN RUNOFF_VALUE ELSE 0 END ) AS M17,
        SUM(CASE WHEN MATURITY_CD='18M' THEN RUNOFF_VALUE ELSE 0 END ) AS M18,
        SUM(CASE WHEN MATURITY_CD='19M' THEN RUNOFF_VALUE ELSE 0 END ) AS M19,
        SUM(CASE WHEN MATURITY_CD='20M' THEN RUNOFF_VALUE ELSE 0 END ) AS M20,
        SUM(CASE WHEN MATURITY_CD='21M' THEN RUNOFF_VALUE ELSE 0 END ) AS M21,
        SUM(CASE WHEN MATURITY_CD='22M' THEN RUNOFF_VALUE ELSE 0 END ) AS M22,
        SUM(CASE WHEN MATURITY_CD='23M' THEN RUNOFF_VALUE ELSE 0 END ) AS M23,
        SUM(CASE WHEN MATURITY_CD='24M' THEN RUNOFF_VALUE ELSE 0 END ) AS M24,
        SUM(CASE WHEN MATURITY_CD='25M' THEN RUNOFF_VALUE ELSE 0 END ) AS M25,
        SUM(CASE WHEN MATURITY_CD='26M' THEN RUNOFF_VALUE ELSE 0 END ) AS M26,
        SUM(CASE WHEN MATURITY_CD='27M' THEN RUNOFF_VALUE ELSE 0 END ) AS M27,
        SUM(CASE WHEN MATURITY_CD='28M' THEN RUNOFF_VALUE ELSE 0 END ) AS M28,
        SUM(CASE WHEN MATURITY_CD='29M' THEN RUNOFF_VALUE ELSE 0 END ) AS M29,
        SUM(CASE WHEN MATURITY_CD='30M' THEN RUNOFF_VALUE ELSE 0 END ) AS M30,
        SUM(CASE WHEN MATURITY_CD='31M' THEN RUNOFF_VALUE ELSE 0 END ) AS M31,
        SUM(CASE WHEN MATURITY_CD='32M' THEN RUNOFF_VALUE ELSE 0 END ) AS M32,
        SUM(CASE WHEN MATURITY_CD='33M' THEN RUNOFF_VALUE ELSE 0 END ) AS M33,
        SUM(CASE WHEN MATURITY_CD='34M' THEN RUNOFF_VALUE ELSE 0 END ) AS M34,
        SUM(CASE WHEN MATURITY_CD='35M' THEN RUNOFF_VALUE ELSE 0 END ) AS M35,
        SUM(CASE WHEN MATURITY_CD='36M' THEN RUNOFF_VALUE ELSE 0 END ) AS M36,
        SUM(CASE WHEN MATURITY_CD='37M' THEN RUNOFF_VALUE ELSE 0 END ) AS M37,
        SUM(CASE WHEN MATURITY_CD='38M' THEN RUNOFF_VALUE ELSE 0 END ) AS M38,
        SUM(CASE WHEN MATURITY_CD='39M' THEN RUNOFF_VALUE ELSE 0 END ) AS M39,
        SUM(CASE WHEN MATURITY_CD='39Mto42M' THEN RUNOFF_VALUE ELSE 0 END ) AS M39toM42,
        SUM(CASE WHEN MATURITY_CD='42Mto45M' THEN RUNOFF_VALUE ELSE 0 END ) AS M42toM45,
        SUM(CASE WHEN MATURITY_CD='45Mto48M' THEN RUNOFF_VALUE ELSE 0 END ) AS M45toM48,
        SUM(CASE WHEN MATURITY_CD='48Mto51M' THEN RUNOFF_VALUE ELSE 0 END ) AS M48toM51,
        SUM(CASE WHEN MATURITY_CD='51Mto54M' THEN RUNOFF_VALUE ELSE 0 END ) AS M51to54M,
        SUM(CASE WHEN MATURITY_CD='54Mto57M' THEN RUNOFF_VALUE ELSE 0 END ) AS M54toM57,
        SUM(CASE WHEN MATURITY_CD='57Mto5Y' THEN RUNOFF_VALUE ELSE 0 END ) AS M57toY5,
        SUM(CASE WHEN MATURITY_CD='5YMto7Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y5MtoY7,
        SUM(CASE WHEN MATURITY_CD='7Yto10Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y7toY10,
        SUM(CASE WHEN MATURITY_CD='10Yto15Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y10toY15,
        SUM(CASE WHEN MATURITY_CD='15Yto20Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y15toY20,
        SUM(CASE WHEN MATURITY_CD='OVER' THEN RUNOFF_VALUE ELSE 0 END ) AS _OVER
 FROM P_KGU_GLM_UES.GLRT_WTE_US t1 left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t2 
  on t1.LEGAL_ENTITY_CD = t2.BRANCH_CD

GROUP BY 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45
)
	union all 
(
SELECT DISTINCT   
              (case 
                      when((reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_UES.GLRT_WTE_US s) )then 'Current Date'
                      else 'Other Dates'
                   end ) as "Current_Date" ,
        REFERENCE_DT,cast(SK_ID_NOCFL as VARCHAR(20)) SK_ID_NOCFL, SCENARIO_CD, SUBSCENARIO_CD,
        FREQ_CD, ORIGINAL_REFERENCE_DT, PROVIDER_CD, 
         FLOW_TYPE_CD, GLRT_SOURCE_CD, TEMPLATE_CD, 
        '020' TEMPLATE_ROW_CD,
        '.   Encumbered Commercial customer' TEMPLATE_ROW_DESC, 
        LEGAL_ENTITY_CD, INTRAGROUP_COUNTERPARTY_IND,
        INTRAGROUP_COUNTERPARTY_CD, FINREP_CPARTY_TYPE_CD, PERIMETER_J_FLAG,
        PERIMETER_C_FLAG, US_INDIVIDUAL_LE_CD, US_CONSOLIDATED_LE_CD,
        t1.COUNTRY_CD, COUNTRY_DESC, ISIN_NUM, BALANCE_SHEET_ITEM_CD, REPORTING_CURRENCY_CD,
        CURRENCY_CD, B3_COUNTERPARTY_TYPE_CD, ASSET_CLASS_CD, COLLATERAL_TYPE_CD,
        CONTRACT_TYPE_CD, GUARANTEE_TYPE_CD, WTE_INSTRUMENT_TYPE_CD,
        ORIGINAL_MATURITY_CD, OPERATION_TYPE_CD, WTE_SIGN,  RETAIL_IND,
         SECURITY_TYPE_CD,
        SPEC_CREDIT_TYPE_CD, INTRAGROUP_COUNTRY_CD, INTRAGROUP_COUNTRY_DESC,
        t2.BRANCH_DESC,
        t2.CATEGORY,
        t2.ASSIGNED_COUNTRY,
        t2.LIQUIDITY_REFERENCE_BANK,
        MIN(ACTUAL_CARRYING_VALUE) AS ACTUAL_CARRYING_VALUE, 
        SUM(ACTUAL_NOMINAL_VALUE) AS ACTUAL_NOMINAL_VALUE,
        SUM(CASE WHEN MATURITY_CD='1M' THEN RUNOFF_VALUE ELSE 0 END ) AS M1,
        SUM(CASE WHEN MATURITY_CD='2M' THEN RUNOFF_VALUE ELSE 0 END ) AS M2,
        SUM(CASE WHEN MATURITY_CD='3M' THEN RUNOFF_VALUE ELSE 0 END ) AS M3,
        SUM(CASE WHEN MATURITY_CD='4M' THEN RUNOFF_VALUE ELSE 0 END ) AS M4,
        SUM(CASE WHEN MATURITY_CD='5M' THEN RUNOFF_VALUE ELSE 0 END ) AS M5,        
        SUM(CASE WHEN MATURITY_CD='6M' THEN RUNOFF_VALUE ELSE 0 END ) AS M6,        
        SUM(CASE WHEN MATURITY_CD='7M' THEN RUNOFF_VALUE ELSE 0 END ) AS M7,        
        SUM(CASE WHEN MATURITY_CD='8M' THEN RUNOFF_VALUE ELSE 0 END ) AS M8,        
        SUM(CASE WHEN MATURITY_CD='9M' THEN RUNOFF_VALUE ELSE 0 END ) AS M9,
        SUM(CASE WHEN MATURITY_CD='10M' THEN RUNOFF_VALUE ELSE 0 END ) AS M10,
        SUM(CASE WHEN MATURITY_CD='11M' THEN RUNOFF_VALUE ELSE 0 END ) AS M11,
        SUM(CASE WHEN MATURITY_CD='12M' THEN RUNOFF_VALUE ELSE 0 END ) AS M12,
        SUM(CASE WHEN MATURITY_CD='13M' THEN RUNOFF_VALUE ELSE 0 END ) AS M13,
        SUM(CASE WHEN MATURITY_CD='14M' THEN RUNOFF_VALUE ELSE 0 END ) AS M14,
        SUM(CASE WHEN MATURITY_CD='15M' THEN RUNOFF_VALUE ELSE 0 END ) AS M15,
        SUM(CASE WHEN MATURITY_CD='16M' THEN RUNOFF_VALUE ELSE 0 END ) AS M16,
        SUM(CASE WHEN MATURITY_CD='17M' THEN RUNOFF_VALUE ELSE 0 END ) AS M17,
        SUM(CASE WHEN MATURITY_CD='18M' THEN RUNOFF_VALUE ELSE 0 END ) AS M18,
        SUM(CASE WHEN MATURITY_CD='19M' THEN RUNOFF_VALUE ELSE 0 END ) AS M19,
        SUM(CASE WHEN MATURITY_CD='20M' THEN RUNOFF_VALUE ELSE 0 END ) AS M20,
        SUM(CASE WHEN MATURITY_CD='21M' THEN RUNOFF_VALUE ELSE 0 END ) AS M21,
        SUM(CASE WHEN MATURITY_CD='22M' THEN RUNOFF_VALUE ELSE 0 END ) AS M22,
        SUM(CASE WHEN MATURITY_CD='23M' THEN RUNOFF_VALUE ELSE 0 END ) AS M23,
        SUM(CASE WHEN MATURITY_CD='24M' THEN RUNOFF_VALUE ELSE 0 END ) AS M24,
        SUM(CASE WHEN MATURITY_CD='25M' THEN RUNOFF_VALUE ELSE 0 END ) AS M25,
        SUM(CASE WHEN MATURITY_CD='26M' THEN RUNOFF_VALUE ELSE 0 END ) AS M26,
        SUM(CASE WHEN MATURITY_CD='27M' THEN RUNOFF_VALUE ELSE 0 END ) AS M27,
        SUM(CASE WHEN MATURITY_CD='28M' THEN RUNOFF_VALUE ELSE 0 END ) AS M28,
        SUM(CASE WHEN MATURITY_CD='29M' THEN RUNOFF_VALUE ELSE 0 END ) AS M29,
        SUM(CASE WHEN MATURITY_CD='30M' THEN RUNOFF_VALUE ELSE 0 END ) AS M30,
        SUM(CASE WHEN MATURITY_CD='31M' THEN RUNOFF_VALUE ELSE 0 END ) AS M31,
        SUM(CASE WHEN MATURITY_CD='32M' THEN RUNOFF_VALUE ELSE 0 END ) AS M32,
        SUM(CASE WHEN MATURITY_CD='33M' THEN RUNOFF_VALUE ELSE 0 END ) AS M33,
        SUM(CASE WHEN MATURITY_CD='34M' THEN RUNOFF_VALUE ELSE 0 END ) AS M34,
        SUM(CASE WHEN MATURITY_CD='35M' THEN RUNOFF_VALUE ELSE 0 END ) AS M35,
        SUM(CASE WHEN MATURITY_CD='36M' THEN RUNOFF_VALUE ELSE 0 END ) AS M36,
        SUM(CASE WHEN MATURITY_CD='37M' THEN RUNOFF_VALUE ELSE 0 END ) AS M37,
        SUM(CASE WHEN MATURITY_CD='38M' THEN RUNOFF_VALUE ELSE 0 END ) AS M38,
        SUM(CASE WHEN MATURITY_CD='39M' THEN RUNOFF_VALUE ELSE 0 END ) AS M39,
        SUM(CASE WHEN MATURITY_CD='39Mto42M' THEN RUNOFF_VALUE ELSE 0 END ) AS M39toM42,
        SUM(CASE WHEN MATURITY_CD='42Mto45M' THEN RUNOFF_VALUE ELSE 0 END ) AS M42toM45,
        SUM(CASE WHEN MATURITY_CD='45Mto48M' THEN RUNOFF_VALUE ELSE 0 END ) AS M45toM48,
        SUM(CASE WHEN MATURITY_CD='48Mto51M' THEN RUNOFF_VALUE ELSE 0 END ) AS M48toM51,
        SUM(CASE WHEN MATURITY_CD='51Mto54M' THEN RUNOFF_VALUE ELSE 0 END ) AS M51to54M,
        SUM(CASE WHEN MATURITY_CD='54Mto57M' THEN RUNOFF_VALUE ELSE 0 END ) AS M54toM57,
        SUM(CASE WHEN MATURITY_CD='57Mto5Y' THEN RUNOFF_VALUE ELSE 0 END ) AS M57toY5,
        SUM(CASE WHEN MATURITY_CD='5YMto7Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y5MtoY7,
        SUM(CASE WHEN MATURITY_CD='7Yto10Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y7toY10,
        SUM(CASE WHEN MATURITY_CD='10Yto15Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y10toY15,
        SUM(CASE WHEN MATURITY_CD='15Yto20Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y15toY20,
        SUM(CASE WHEN MATURITY_CD='OVER' THEN RUNOFF_VALUE ELSE 0 END ) AS _OVER
 FROM P_KGU_GLM_UES.GLRT_WTE_US t1 left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t2 
  on t1.LEGAL_ENTITY_CD = t2.BRANCH_CD
 where  trim(TEMPLATE_ROW_CD) in ('020a', '020b' ,'020c')

GROUP BY 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45

)

) by teradata;

/************************************
			END USER CODE 
************************************/
%mend;