package com.myapp.mekvahan.CommonFiles;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.AllBookingsDashboard.TrackOrder2;
import com.myapp.mekvahan.HomePage.AppHomePage;
import com.myapp.mekvahan.R;

import java.util.ArrayList;
import java.util.List;

public class AllDialog {

    public static final String LOGIN_FAQ           = "login_faq";
    public static final String TERMS_AND_CONDITION = "terms_and_conditions";
    public static final String TERMS_OF_USE        = "terms_of_use";
    public static final String PRIVACY_POLICY      = "privacy_policy";
    public static final String DISCLAIMER          = "disclaimer";
    public static final String GENERAL_QUERIES     = "general_queries";
    public static final String WALLET_FAQ          = "wallet_faq";

    private final String TAG = getClass().getSimpleName();
    private Context mCtx;

    public AllDialog(Context context){
        mCtx = context;
    }



    //All faq
    public void AllStaticPages(String type) {

        final Dialog dialog = new Dialog(mCtx);
        View view;
        view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_mek_faq, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BillAnimation1;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        String title = "";

        if(type.equals(LOGIN_FAQ))
            title = "Mek FAQ";
        else if (type.endsWith(TERMS_AND_CONDITION))
            title = "Terms And Condition";
        else if (type.endsWith(TERMS_OF_USE))
            title = "Terms Of Use";
        else if (type.endsWith(PRIVACY_POLICY))
            title = "Privacy Policy";
        else if (type.endsWith(DISCLAIMER))
            title = "Disclaimer";
        else if (type.endsWith(GENERAL_QUERIES))
            title = "General Queries";
        else if(type.endsWith(WALLET_FAQ))
            title = "FAQ Wallet";

        ((TextView)dialog.findViewById(R.id.title)).setText(title);

        List<StaticData> list = prepareContent(type);
        setFaqAdapter(view, list);


        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void setFaqAdapter(View faqView,List <StaticData> list) {

        RecyclerView recyclerView = faqView.findViewById(R.id.faqRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

        StaticDataAdapter adapter = new StaticDataAdapter(mCtx,list);
        recyclerView.setAdapter(adapter);



    }


    private List<StaticData> prepareContent(String type) {

        List<StaticData> list = new ArrayList<>();

        if(type.equals(LOGIN_FAQ)) {

            list.add(new StaticData(1,"Why Mekvahan vehicle servicing?"));
            list.add(new StaticData(2,"A curated set of service network partners who follow tightly defined rules and regulations to deliver superior customer experience. Few other benefits to you -\n" +
                    "a. Book a service at any time from Mekvahan website or mobile app & get an instant confirmation.\n" +
                    "b. Doorstep pick up and drop by Mekvahan service providers.\n" +
                    "c. All details about the vehicle (information, pictures, insurance copy, RC if any) captured by the Mekvahan users on the website & mobile apps are used and verified instantly by the service partners and the providers.\n" +
                    "d. All estimates, revised estimates, work detail, service engineer etc are uploaded by the service partners on the Mekvahan Service Partner web & mobile application.\n" +
                    "e. Customers can eventually pay at his doorstep by card or cash. Mekvahan provides an advisory layer for the customer by using historical aggregated data to advise the customer about any abnormal jobs being undertaken by the service center. Mekvahan has an expert team, which then coordinates to clarify and satisfy the customer about these issues.\n"));
            list.add(new StaticData(1,"1. Is Mekvahan a service center?\n"));
            list.add(new StaticData(2,"We don’t own physical service centers. Mekvahan is a stack that has partnered with authorized, multi-brand and independent garages with defined specifications and expertise. They have curated their specialization and listed on the Mekvahan platform. We prefer to empower customers with the choice to select their service center depending on the job requirements and customer priorities.\n"));
            list.add(new StaticData(1," 2. How is Mekvahan better than other aggregators? \n"));
            list.add(new StaticData(2,"Mekvahan doesn’t confuse customers with too many options. We keep it simple with few options with best-suited structure and coverage for your vehicle at a very competitive price available in the market. Mekvahan acts as a single point of contact to manage any repair, maintenance service or damage with specifications  in an organized structured way.\n"));
            list.add(new StaticData(1,"3. Why Mekvahan roadside assistance? \n"));
            list.add(new StaticData(2,"You can avail emergency assistance (onsite or roadside assistance) on click of a button for towing, breakdown, key recovery, out of fuel, door lock and etc. Most importantly you don’t need any subscription to avail this service and you can track help arriving to you when your family or you are in a distress situation. Always be assured of a Mekvahan assist partner to be around you and reach you within 45 minutes. No more calls and queues when you need quick assistance.\n"));
            list.add(new StaticData(1,"4. Pickup and drop off service of my vehicle is chargeable or free?\n"));
            list.add(new StaticData(2,"Absolutely free, Mekvahan gives you free pickup and drop off service (validating certain T&Cs).\n"));
            list.add(new StaticData(1,"5. Which parts and inventory we use?\n"));
            list.add(new StaticData(2,"Mekvahan uses only genuine parts and inventory (supplied directly from verified OEMs or OESs) for customer benefit.\n"));
            list.add(new StaticData(1,"6. Are we providing any warranty period?\n"));
            list.add(new StaticData(2,"Our every kinds of services comes under the particular warranty period (based upon the class of service availed).\n"));
            list.add(new StaticData(1,"7. Which type of cars and bikes do we service?\n"));
            list.add(new StaticData(2,"We service every kind of cars and bikes with accuracy and transparency and if you don’t find any model on our website or mobile app then contact us with our support team 24*7  (+91-7838878888) and get an instant quote.\n"));
            list.add(new StaticData(1,"8. What is our service hours?\n"));
            list.add(new StaticData(2,"We give 24*7 services to our esteemed customers.\n"));
            list.add(new StaticData(1,"9. Which engine oil grade do we use in the car or bike service?\n"));
            list.add(new StaticData(2,"We use best suitable grades of the top brands available in the market according to the need and model of the vehicle for the engine oil requirements.\n"));

//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));

        }

        else if(type.equals(TERMS_AND_CONDITION)){

            list.add(new StaticData(2,"This document describes policy of Mekvahan.com (hereafter referred to as  Mekvahan ) regarding information received by Mekvahan during your visits to our website or App. The amount and type of information received depends on how one uses the site or app. Mekvahan is a website/app which is the property of Udyamat Ma Viramsava Pvt. Ltd. a company registered under the Companies Act, 2013, having its registered office at Aya Nagar Extn., Delhi - 110047.\n"));
            list.add(new StaticData(1,"What is Mekvahan.com?\n"));
            list.add(new StaticData(2,"Mekvahan.com is a Online Platform / Public forum which is completely into an automotive sector that provides different types of regular services as well as SOS services (On road and roadside assistance), also known as emergency services .\n"));
            list.add(new StaticData(1,"'Customer'\n"));
            list.add(new StaticData(2,"Customer is a visitor on Mekvahan.com website or Mekvahan app who is a registered member and has filled up the form on the website, is not a dealer or not a partner of dealer or not an associate of dealer or who has no objection contacting any car/bike dealer for his car/bike servicing.\n"));
            list.add(new StaticData(1,"'Service Vendor' / 'Car/bike Vendor' / 'Car/bike Service Vendor'\n"));
            list.add(new StaticData(2,"A workshop, motor vehicle service workshop which deals with motor vehicle servicing, repairing and/or provides motor vehicle servicing related facility."));
            list.add(new StaticData(1,"'Franchisee', 'Distributor'\n"));
            list.add(new StaticData(2,"A representative who represents Mekvahan cities all across India. A representative has Distributor agreement with Mekvahan to operate his office using brand and help Mekvahan customers all across India\n"));
            list.add(new StaticData(1,"Mekvahan.com Objective - Comfort and Choice\n"));
            list.add(new StaticData(2,"Providing Customers with a platform to help customer members to book services online  and then the service is done by registered motor vehicle service vendors authorised/certified by Mekvahan. Mekvahan.com is using technology available for WEB and Mobile to allow them to send enquiries and get response from motor vehicle service vendors. Mekvahan is working on CUSTOMER CHOICE to allow customers to choose the best service for their motor vehicle.\n"));
            list.add(new StaticData(2,"Value Added Products and Services (VAPS) are provided by Mekvahan for the benefit of customers by associating with other companies. These are products supplied by Mekvahan as it is as per suppliers/manufacturer's terms and conditions, and Mekvahan acts as a reseller dealer of that products or services.\n"));
            list.add(new StaticData(1,"'Leads'"));
            list.add(new StaticData(2,"Lead means enquiry generated through online/ offline promotion. It is categorised as a QualifiedLead”if the name of the customer and its contact information is correct. “Leadand lead generation does not mean customers will use your services, in other words, a lead can be considered as conversion, but it may not necessarily result in a Customer actually visiting a dealer. Leads in all agreements with Service vendors/associates/partners under lead generation program or revenue share program are qualified leads."));
            list.add(new StaticData(2,"NOTE: All terms and conditions are applicable to Every page on this domain http://www.mekvahan.com even if it is not mentioned on the page, all visitors must consider this as assumption."));
            list.add(new StaticData(1,"How It Works\n"));
            list.add(new StaticData(2,"Mekvahan.com asks a customer to opt for any particular service available on our platform and our authorised/certified dealer will service the motor vehicle of the customer . Mekvahan also for its esteemed customers try to contact and list down all best and authorised motor vehicle service vendors, but it is not necessary that all service vendors may be shown in the list before they decide to become Registered Motor Vehicle Dealer Member. Customer may recommend car/bike dealer and Mekvahan.com would try to make them as Registered Motor Vehicle Dealer Member as per the Mekvahan.com Terms and Conditions.\n"));
            list.add(new StaticData(1,"Technology\n"));
            list.add(new StaticData(2,"Mekvahan.com is using Standard technology (Web and Mobile) available to meet the objective. Mekvahan.com is associated with companies for Web Hosting, Domain, Mobile SMS Server. The Promoter and Promoter Affiliates of the Company are not liable for any problems or technical malfunction of any telephone network or lines, computer online systems, servers or providers, computer equipment, software, technical problems or traffic congestion on the Internet / Mobile network or at any website, or any combination thereof, including any injury or damage to participants or any other person's computer related to or resulting from sending booking enquiry or participation in or downloading any materials from the website.To secure web forms, Mekvahan.com technical team use secure code and caches to prevent hacking or mis-use of web form and update code from time-to-time. In case of receipt of any unwanted mail from Mekvahan domain should be informed with a URL of the page to help us preventing mis-use of forms.\n"));
            list.add(new StaticData(1,"Insurance\n"));
            list.add(new StaticData(2,"It is the Customers responsibility to ensure that their vehicles are properly insured at the time they contact Mekvahan.com. Mekvahan shall in no manner be responsible for any deficiency in service that the dealer may provide, and no indemnification of any loss shall be given by Mekvahan.\n"));
            list.add(new StaticData(1,"Recommendations\n"));
            list.add(new StaticData(2,"Mekvahan.com may recommend some facility to their customers using automatic selection by the program. Presently it program/software which  recommends  based on two criteria 1.  location  and 2.  member . Location is city  and Member is   Service vendors/auto repair shop  which is registered with the website. The Mekvahan team is also recommending Customers who call offline on the Mekvahan Helpline. The reason for including Members in the recommendation is to allow  verified  workshops to get enquires rather  unverified . Members are updated regularly with their contact details and a part of verified directories on the website. With times, Mekvahan will have ratings and feedback from  real  customers, the website will update  software/program  to recommend customer based on `` rating'' and `` location''  and  members . Since Online services are free to send enquiries, so customer needs to decide based on his requirements for car/bike servicing or other repairs.\n"));
            list.add(new StaticData(1,"Privacy Policy\n"));
            list.add(new StaticData(2,"Mekvahan.com keeps customer data private on a secure database and allows motor vehicle service vendors to display information which resides on the Mekvahan.com web server. A dealer can edit information using Mekvahan.com functionality.\n" +
                    "Customer's emails and registration information are not sold or provided to anybody and are kept confidential.\n" +
                    "Customer can search for the service and our authorised Service vendors will send them booking enquiries for confirmation. Mekvahan.com plays an important role of an interface between Customer and car/bike Service vendors and keeps a record of communications / details exchange between them for monitoring purposes.\n"));
            list.add(new StaticData(1,"WEBSITE AND APP TERMS AND CONDITIONS OF USE\n"));
            list.add(new StaticData(1,"1. ACCEPTANCE OF TERMS\n"));
            list.add(new StaticData(2,"Mekvahan provides data for customer to search and send enquiry to motor vehicle service vendors and display a collection of online resources, including auto news, ads, quiz & updates (referred to hereafter as the Service) subject to the following terms and conditions (Terms). By using the Service in any way, you are agreeing to comply with these Terms. In addition, when using particular Mekvahan services, you agree to abide by any applicable posted guidelines for all Mekvahan services, which may change from time to time.\n" +
                    "You should object to any term or condition of these Terms, any guidelines, or any subsequent modifications there to or become dissatisfied with Mekvahan in any way, your only recourse is to immediately discontinue use of Mekvahan. You are, however, encouraged to inform/complain to our customer care team who will do their best to address your concern/s.\n"));
            list.add(new StaticData(1,"2. MODIFICATIONS TO THIS AGREEMENT\n"));
            list.add(new StaticData(2,"We reserve the right, at our sole discretion, to change, modify or otherwise alter these terms and conditions at any time. Such modifications shall become effective immediately upon the posting thereof. You must review this agreement on a regular basis to keep yourself apprised of any changes. You can find the most recent version of these Terms at: www.Mekvahan.com\n"));
            list.add(new StaticData(1,"3. CONTENT\n"));
            list.add(new StaticData(2,"The content at Mekvahan.com collects information provided and created by motor vehicle Service vendors / advertisers, content partners, software developers, publishers, marketing agents, employees, users, resellers and other third parties. While every attempt has been made to ascertain the authenticity of the content of the Website,Mekvahan has no control over the accuracy of such information on our pages, and material on the Mekvahan.com. Web site may include technical inaccuracies or typographical errors, and we make no guarantees, nor can we be responsible for any such information, including its authenticity, currency, content, quality, copyright compliance or legality, or any resulting loss or damage. All of the data on motor vehicle services, insurance, problems and Promotions including but not limited to, the prices and the availability of any product or service or any feature thereof, is subject to change without notice by the party providing the Product or Promotion. You should use discretion while browsing the Internet.\n" +
                    "Mekvahan.com reserves the right, in its sole discretion and without any obligation, to make improvements to, or correct any error or omissions in any portion of the Site. Where appropriate, we will endeavour to update information listed on the Web site on a timely basis, but shall not be liable for any inaccuracies.\n" +
                    "All rights, title and interest including trademarks and copyrights in respect of the domain name and site content hosted on the Website are reserved with Mekvahan.com. Users are permitted to read, print or download text, data and/or graphics from the site for their personal use only. Unauthorized access, reproduction, redistribution, transmission and/or dealing with any information contained in this site in any other manner, either in whole or in part, are strictly prohibited, failing which strict legal action will be initiated against such users.\n" +
                    "You understand that all postings, motor vehicle dealer data, messages, text, files, images, photos, videos, sounds, or other materials (Content) posted on, transmitted through, or linked from the Service, are the sole responsibility of the person from whom such Content originated. More specifically, you are entirely responsible for all Content that you post, email or otherwise make available via the Service. You understand that Mekvahan does not entirely control, and is not responsible for Content made available through the Service, and that by using the Service, you may be exposed to Content that is offensive, indecent, inaccurate, misleading, or otherwise objectionable. Furthermore, the Mekvahan site and Content available through the Service may contain links to other websites, which are completely independent of Mekvahan. Mekvahan makes no representation or warranty as to the accuracy, completeness or authenticity of the information contained in any such site.\n" +
                    "Mekvahan makes NO representation that any Manufacturer / car/bike bands or any other and its associate in any manner until clearly specify kind of association or agreement with associates directly or indirectly on the directory/web pages.\n" +
                    "Your linking to any other website is at your own risk. You agree that you must evaluate, and bear all risks associated with the use of any Content, that you may not rely on said Content, and that under no circumstances will Mekvahan be liable in any way for any Content or for any loss or damage of any kind incurred as a result of the use of any Content posted, emailed or otherwise made available via the Service. You acknowledge that Mekvahan does not always pre-screen or approve Content (unless specified), but that Mekvahan shall have the right (but not the obligation) in its sole discretion to refuse, delete or move any Content that is available via the Service, for violating the letter or spirit of the Terms or for any other reason.\n" +
                    "\n" +
                    "Copyright Logos/Material\n" +
                    "Mekvahan.com has an agreement with Service Provider (authorized service station, Service vendors, outlets, workshops) and promotes their business by displaying their logos/their service logos and other copyrighted material. Copyrighted logos and pictures are displayed by Mekvahan.com by taking permission from Service Provider with agreement of promoting their business. Mekvahan.com has no direct association or agreement with manufacturer.\n"));
            list.add(new StaticData(1,"4.1. Customer Disputes with workshops/outlets/shops\n"));
            list.add(new StaticData(2,"Mekvahan.com registers motor vehicle workshops and promotes them. Service vendors (motor vehicle workshop, motor vehicle outlet) which are providing services to customers (routed through Mekvahan website or local call center) shall be considered THIRD PARTY SERVICES. All Service vendors in the directory are easy to search based on city, area. Mekvahan deals with Service vendors and offer their services to customers as mediator as concept. It is not necessarily that all are best in services. Mekvahan.com will also display Customers reviews and suggest service centre based on the available data. In absence of reviews of new service center, it shall be the Customers responsibility to talk to the manager directly and get information at their website page available. Mekvahan.com does not take any responsibility for maintenance issues and quality of services but may help customer to resolve issue if possible with mutual agreements between customers and Service vendors. We hope to build a transparent system online with times to allow all customers (visiting Mekvahan.com website) to give fair feedback and reviews. CUSTOMER CAN NOT CLAIM for any dispute with Mekvahan.com for motor vehicle servicing issues and other dealer disputes. Mekvahan.com has policy to remove Dealer's Business Name from the recommendation list when found customer dissatisfied based on Mekvahan criteria of removal or/and Dealer Termination Policy from the list. Mekvahan criteria of removal from the list is of unhappy customers or/and NON - RESPONSIVE to customer issues.\n"));
            list.add(new StaticData(2,"4.2 ALL DISPUTES: All legal disputes with customers, Service vendors, associates and any third party or any service related shall be in jurisdiction of Delhi, India. All disputes shall be intimated at our address info [at] Mekvahan.com or sent at the registered office. All disputes are to be resolved by arbitration under the Arbitration & Conciliation Act, 1996 (as amended from time to time.)\n"));
            list.add(new StaticData(1,"5. NOTIFICATION OF CLAIMS OF INFRINGEMENT\n"));
            list.add(new StaticData(2,"If you believe that your work has been copied in a way that constitutes copyright infringement, or your intellectual property rights have been otherwise violated, please notify us at info [at] mekvahan.com\n"));
            list.add(new StaticData(1,"6. PRIVACY AND INFORMATION DISCLOSURE\n"));
            list.add(new StaticData(2,"Mekvahan has established a Privacy Policy to explain to users how their information is collected and used, which is located at the following web address:\n" +
                    "www.mekvahan.com\n" +
                    "Your use of the Mekvahan website or the Service signifies acknowledgement of and agreement to our Privacy Policy. You further acknowledge and agree that Mekvahan may, in its sole discretion, preserve or disclose your Content, as well as your information, such as email addresses, IP addresses, timestamps, and other user information, if required to do so by law or in the good faith or belief that such preservation or disclosure is reasonably necessary to: comply with legal process; enforce these Terms; respond to claims that any Content violates the rights of third-parties; respond to claims that contact information (e.g. phone number, street address) of a third-party has been posted or transmitted without their consent or as a form of harassment; protect the rights, property, or personal safety of Mekvahan, its users or the general public.\n"));
            list.add(new StaticData(1,"6.1 Google Advertising Cookie and Privacy Policies\n"));
            list.add(new StaticData(2,"We use third-party advertising companies to serve ads when you visit our website. These companies may use information (not including your name, address, email address, or telephone number) about your visits to this and other websites in order to provide advertisements about goods and services of interest to you. If you would like more information about this practice and to know your choices about not having this information used by these companies, click here\n"));
            list.add(new StaticData(1,"6.2 Communication Mediums\n"));
            list.add(new StaticData(2,"You agree to receive promotional, transactional and commercial communications from Mekvahan through email,SMS and voice.\n"));
            list.add(new StaticData(1,"7. CONDUCT\n"));
            list.add(new StaticData(2,"You agree not to post, email, or otherwise make available Content:\n" +
                    "1. That is unlawful, harmful, threatening, abusive, harassing, defamatory, libelous, invasive of another's privacy, or is harmful to minors in any way.\n" +
                    "2. That is pornographic in nature.\n" +
                    "3. That harasses, degrades, intimidates or is hateful toward an individual or group of individuals on the basis of religion, gender, sexual orientation, race, ethnicity, age, or disability.\n" +
                    "4. That impersonates any person or entity, including, but not limited to, a Mekvahan employee, or falsely state or otherwise misrepresent your affiliation with a person or entity (this provision does not apply to messages that are lawful non-deceptive parodies of public figures.).\n" +
                    "5. That includes personal or identifying information about another person without that person's explicit consent.\n" +
                    "6. That is false, deceptive, misleading, deceitful, misinformative, or constitutes bait and switch.\n" +
                    "7. That infringes on any patent, trademark, trade secret, copyright or other proprietary rights of any party, or Content that you do not have a right to make available under any law or under contractual or fiduciary relationships.\n" +
                    "8. That constitutes or contains affiliate marketing, link referral code, junk mail, spam, chain letters, pyramid schemes, or unsolicited commercial advertisement.\n" +
                    "9. That constitutes or contains any form of advertising or solicitation if: posted in areas of the Mekvahan sites which are not designated for such purposes; or emailed to Mekvahan users who have not indicated in writing that it is ok to contact them about other services, products or commercial interests.\n" +
                    "10. That includes links to commercial services or web sites, except as allowed in services.\n" +
                    "11. That advertises any illegal service or the sale of any items the sale of which is prohibited or restricted by any applicable law, including without limitation items the sale of which is prohibited or regulated by Indian law.\n" +
                    "12. That contains software viruses or any other computer code, files or programs designed to interrupt, destroy or limit the functionality of any computer software or hardware or telecommunications equipment.\n" +
                    "13. That disrupts the normal flow of dialogue with an excessive number of messages (flooding attack) to the Service, or that otherwise negatively affects other users' ability to use the Service.\n" +
                    "14. That employs misleading email addresses, or forged headers or otherwise manipulated identifiers in order to disguise the origin of Content transmitted through the Service.\n" +
                    "15. Additionally, you agree not to:\n" +
                    "16. Contact anyone who has asked not to be contacted.\n" +
                    "Stalk or otherwise harass anyone.\n" +
                    "16. Collect personal data about other users for commercial or unlawful purposes;\n" +
                    "17. Use automated means, including spiders, robots, crawlers, data mining tools, or the like to download data from the Service - unless expressly permitted by Mekvahan.\n" +
                    "18. Post non-local or otherwise irrelevant Content, repeatedly post the same or similar Content or otherwise impose an unreasonable or disproportionately large load on our infrastructure.\n" +
                    "19. Attempt to gain unauthorized access to Mekvahan's computer systems or engage in any activity that disrupts, diminishes the quality of, interferes with the performance of, or impairs the functionality of, the Service or the Mekvahan website. \n" +
                    "20. Use any form of automated device or computer program that enables the submission of postings on Mekvahan without each posting being manually entered by the author thereof (an automated posting device), including without limitation, the use of any such automated posting device to submit postings in bulk, or for automatic submission of postings at regular intervals.\n"));
            list.add(new StaticData(1,"8. NO SPAM POLICY\n"));
            list.add(new StaticData(2,"You understand and agree that sending unsolicited email advertisements to Mekvahan email addresses or through Mekvahan computer systems, which is expressly prohibited by these Terms, will use or cause to be used Mekvahan servers. Any unauthorized use of Mekvahan computer systems is a violation of these Terms and certain federal and state laws. Such violations may subject the sender and his or her agents to civil and criminal penalties.\n"));
            list.add(new StaticData(1,"9. LIMITATIONS ON SERVICE\n"));
            list.add(new StaticData(2,"You acknowledge that Mekvahan may establish limits concerning use of the Service, including the maximum number of days that Content will be retained by the Service, the maximum number and size of postings, email messages, or other Content that may be transmitted or stored by the Service, and the frequency with which you may access the Service. You agree that Mekvahan has no responsibility or liability for the deletion or failure to store any Content maintained or transmitted by the Service. You acknowledge that Mekvahan reserves the right at any time to modify or discontinue the Service (or any part thereof) with or without notice, and that Mekvahan shall not be liable to you or to any third party for any modification, suspension or discontinuance of the Service.\n"));
            list.add(new StaticData(1,"10. ACCESS TO THE SERVICE\n"));
            list.add(new StaticData(2,"Mekvahan grants you a limited, revocable, non-exclusive license to access the Service for your own personal use. This license does not include any collection, aggregation, copying, duplication, display or derivative use of the Service nor any use of data mining, robots, spiders, or similar data gathering and extraction tools for any purpose unless expressly permitted by Mekvahan. A limited exception is provided to general purpose internet search engines and non-commercial public archives that use such tools to gather information for the sole purpose of displaying hyperlinks to the Service, provided they each do so from a stable IP address or range of IP addresses using an easily identifiable agent and comply with our robots.txt file. General purpose internet search engine does not include a website or search engine or other service that specializes in classified listings or in any subset of classifieds listings such as jobs, housing, for sale, services, or personnel, or which is in the business of providing classified ad listing services.\n" +
                    "Mekvahan permits you to display on your website, or create a hyperlink on your website to, individual postings on the Service so long as such use is for non-commercial and/or news reporting purposes only (e.g., for use in personal web blogs or personal online media).\n"));
            list.add(new StaticData(1,"11. TERMINATION OF SERVICE\n"));
            list.add(new StaticData(2,"You agree that Mekvahan, in its sole discretion, has the right (but not the obligation) to delete or deactivate your account, block your email or IP address, or otherwise terminate your access to or use of the Service (or any part thereof), immediately and without notice, and remove and discard any Content within the Service, for any reason, including, without limitation, if Mekvahan believes that you have acted inconsistently with the letter or spirit of the Terms. Further, you agree that Mekvahan shall not be liable to you or any third-party for any termination of your access to the Service. Further, you agree not to attempt to use the Service after said termination. Sections 2, 4, 6 and 10-16 shall survive termination of these Terms.\n" +
                    "Please read clause 32 (below) for termination period for customer,dealer, other companies.\n"));
            list.add(new StaticData(1,"12. PROPRIETARY RIGHTS\n"));
            list.add(new StaticData(2,"The Service is protected to the maximum extent permitted by copyright laws and international treaties. Content displayed on or through the Service is protected by copyright as a collective work and/or compilation, pursuant to copyright laws, and international conventions. Any reproduction, modification, creation of derivative works from or redistribution of the site or the collective work, and/or copying or reproducing the sites or any portion thereof to any other server or location for further reproduction or redistribution is prohibited without the express written consent of Mekvahan. You further agree not to reproduce, duplicate or copy Content from the Service without the express written consent of Mekvahan, and agree to abide by any and all copyright notices displayed on the Service. You may not decompile or disassemble, reverse engineer or otherwise attempt to discover any source code contained in the Service. Without limiting the foregoing, you agree not to reproduce, duplicate, copy, sell, resell or exploit for any commercial purposes, any aspect of the Service. \n" +
                    "\n" +
                    "Although Mekvahan does not claim ownership of content that its users post, by posting Content to any public area of the Service, you automatically grant, and you represent and warrant that you have the right to grant, to Mekvahan an irrevocable, perpetual, non-exclusive, fully paid, worldwide license to use, copy, perform, display, and distribute said content and to prepare derivative works of, or incorporate into other works, said content, and to grant and authorize sublicenses (through multiple tiers) of the foregoing. Furthermore, by posting content to any public area of the Service, you automatically grant Mekvahan all rights necessary to prohibit any subsequent aggregation, display, copying, duplication, reproduction, or exploitation of the Content on the Service by any party for any purpose.\n"));
            list.add(new StaticData(1,"13. DISCLAIMER OF WARRANTIES\n"));
            list.add(new StaticData(2,"YOU AGREE THAT the USE OF THE Mekvahan SITE AND THE SERVICE IS ENTIRELY AT YOUR OWN RISK. THE Mekvahan SITE AND THE SERVICE ARE PROVIDED ON AN AS IS OR AS AVAILABLE BASIS, WITHOUT ANY WARRANTIES OF ANY KIND. ALL EXPRESS AND IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NON-INFRINGEMENT OF PROPRIETARY RIGHTS ARE EXPRESSLY DISCLAIMED TO THE FULLEST EXTENT PERMITTED BY LAW. TO THE FULLEST EXTENT PERMITTED BY LAW, Mekvahan DISCLAIMS ANY WARRANTIES FOR THE SECURITY, RELIABILITY, TIMELINESS, ACCURACY, AND PERFORMANCE OF THE Mekvahan SITE AND THE SERVICE. TO THE FULLEST EXTENT PERMITTED BY LAW, Mekvahan DISCLAIMS ANY WARRANTIES FOR OTHER SERVICES OR GOODS RECEIVED THROUGH OR ADVERTISED ON THE Mekvahan SITE OR THE SITES OR SERVICE, OR ACCESSED THROUGH ANY LINKS ON THE Mekvahan SITE. TO THE FULLEST EXTENT PERMITTED BY LAW, Mekvahan DISCLAIMS ANY WARRANTIES FOR VIRUSES OR OTHER HARMFUL COMPONENTS IN CONNECTION WITH THE Mekvahan SITE OR THE SERVICE. Some jurisdictions do not allow the disclaimer of implied warranties. In such jurisdictions, some of the foregoing disclaimer may not apply to you in so far as they relate to implied warranties.\n"));
            list.add(new StaticData(1,"14. LIMITATIONS OF LIABILITY\n"));
            list.add(new StaticData(2,"UNDER NO CIRCUMSTANCES SHALL Mekvahan BE LIABLE FOR DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES (EVEN IF Mekvahan HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES), RESULTING FROM ANY ASPECT OF YOUR USE OF THE Mekvahan SITE OR THE SERVICE, WHETHER THE DAMAGES ARISE FROM the USE OR MISUSE OF THE Mekvahan SITE OR THE SERVICE, FROM INABILITY TO USE THE Mekvahan SITE OR THE SERVICE, OR THE INTERRUPTION, SUSPENSION, MODIFICATION, ALTERATION, OR TERMINATION OF THE Mekvahan SITE OR THE SERVICE. SUCH LIMITATION SHALL ALSO APPLY WITH RESPECT TO DAMAGES INCURRED BY REASON OF OTHER SERVICES OR PRODUCTS RECEIVED THROUGH OR ADVERTISED IN CONNECTION WITH THE Mekvahan SITE OR THE SERVICE OR ANY LINKS ON THE Mekvahan SITE, AS WELL AS BY REASON OF ANY INFORMATION OR ADVICE RECEIVED THROUGH OR ADVERTISED IN CONNECTION WITH THE Mekvahan SITE OR THE SERVICE OR ANY LINKS ON THE Mekvahan SITE. THESE LIMITATIONS SHALL APPLY TO THE FULLEST EXTENT PERMITTED BY LAW. In some jurisdictions, limitations of liability are not permitted. In such jurisdictions, some of the foregoing limitation may not apply to you.\n"));
            list.add(new StaticData(1,"15. INDEMNITY\n"));
            list.add(new StaticData(2,"You agree to indemnify and hold the Company, its officers, subsidiaries, affiliates, successors, assigns, directors, officers, agents, service providers, suppliers and employees, harmless from any claim or demand, including reasonable attorney fees and court costs, made by any third party due to or arising out of Content you submit, post or make available through the Service, your use of the Service, your violation of the Terms, your breach of any of the representations and warranties herein, or your violation of any rights of another.\n"));
            list.add(new StaticData(1,"16. GENERAL INFORMATION\n"));
            list.add(new StaticData(2,"The Terms constitute the entire agreement between you and Mekvahan and govern your use of the Service, superseding any prior agreements between you and Mekvahan. The Terms and the relationship between you and Mekvahan shall be governed by the Indian laws without regard to its conflict of law provisions. You and Mekvahan agree to submit to the personal and exclusive jurisdiction of the courts located within India. The failure of Mekvahan to exercise or enforce any right or provision of the Terms shall not constitute a waiver of such right or provision. If any provision of the Terms is found by a court of competent jurisdiction to be invalid, the parties nevertheless agree that the court should endeavour to give effect to the parties' intentions as reflected in the provision, and the other provisions of the Terms remain in full force and effect. You agree that regardless of any statute or law to the contrary, any claim or cause of action arising out of or related to use of the Service or the Terms must be filed within one (1) year after such claim or cause of action arose or be forever barred.\n" +
                    "The Promoter and Promoter Affiliates are not liable for any problems or technical malfunction of any telephone network or lines, computer online systems, servers or providers, computer equipment, software, technical problems or traffic congestion on the Internet or at any website, or any combination thereof, including any injury or damage to participants or any other person's computer related to or resulting from participation in or downloading any materials in this promotion. This does not apply to liability for fraud, gross negligence or for death or personal injury caused by the Promoter or Promoter's Affiliates negligence or any other liability which cannot be excluded by law. The Promoter is Udyamat Ma Viramasva Pvt. Ltd. having its registered office at Ph-4, Aya Nagar Extn.,Delhi-110047, CIN: U63030DL2018PTC333382, PAN : AACCU2431Q\n" +
                    "Mekvahan.com concept is to give choices to customers and customer is not bound to go to any particular outlets/company.\n"));
            list.add(new StaticData(1,"19. Terms and conditions for Motor Vehicle Helpline- On-Road Assistance\n"));
            list.add(new StaticData(2,"Mekvahan associated with Car/bike On-Road Emergency Service Companies and do not provide this service itself.\n" +
                    "1. This service is provided as per terms and conditions stated by Car/bike Emergency Breakdown Company.\n" +
                    "2. All disputes arising from this value added services should be dealt directly with associated companies.\n" +
                    "3. Services are available within border areas of Delhi & NCR.\n" +
                    "4. Services will be provided only when the vehicle develops fault while on the road. Mekvahan does not guarantee any reach time for service provided.\n" +
                    "5. It’s mandatory for the customer making helpline call to produce either the card or the sticker to avail the required services.\n" +
                    "6. If the vehicle is repairable on road, free towing/dropping service will not be provided.\n" +
                    "7. Wheel tube repair services will be provided for a flat tyre at actual cost.\n" +
                    "8. Claims/Refunds if any will not be exceed the residual value (on per day prorated basis of Card validity period) of the card fees paid by the Cardholder as on the date of the claim.\n" +
                    "9. It will be considered that all the terms & conditions are accepted by the cardholder once the services are utilized by him even if the card subscription form does not reach this office duly filled in and signed by the Cardholder concerned.\n" +
                    "10. Service of opening of cars with keys locked inside or bike key lost will be given if possible with standard tools available.\n" +
                    "11. Towing of vehicle may not be provided between 8 am to 11 am and 5 pm to 8 pm or any other restriction imposed by the traffic police, and towing shall at no point of time be free. The Customer will have to pay the rate stipulated.\n" +
                    "12. Vehicles older than 15 years at the time of enrolling shall not be accepted for service.\n" +
                    "13. The company will try and ensure to provide service within the stipulated time of 30 minutes but it cannot be held responsible for any delay arising due to conditions not within its control.\n" +
                    "14. The customer or its representative will have no claim against the company or its representative either civil or criminal nature or pursuant to this card in respect of death or injury of the customer or any other person or loss or damage to any other property caused by or due to equipment failure, breakdown or accident or fire on utilization of services during the validity of the card.\n" +
                    "15. Receipts for cost of Petrol/Diesel will not be provided.\n" +
                    "\n" +
                    "Above terms are general for all car/bike breakdown memberships. It varies for different vendors/companies, please check offers detail when you receive car/bike breakdown card membership.\n"));
            list.add(new StaticData(1,"20. Special Offers Campaign / Hot Deals\n"));
            list.add(new StaticData(2,"20.1.Mekvahan runs campaigns from time to time at all occasions for awareness and promotional purposes.\n" +
                    "20.2. Motor Vehicle owners need to provide information required to be eligible for FREE & Discount offers\n" +
                    "20.3. Free Motor Vehicle Servicing coupons are sent through courier or web based service. These are limited time campaigns.\n" +
                    "20.4. Offers are limited to areas, please call Mekvahan helpline to check areas and Service vendors and company associated with value added services.\n" +
                    "20.5. Mekvahan promotes associates companies in the offer. Please check separate terms and conditions of partner companies.\n" +
                    "20.6. Please read the terms and conditions before buying offers online. Mekvahan.com cannot guarantee for other services not mentioned in the offer.\n" +
                    "20.7. Once purchase any offer online, Mekvahan.com team dispatch coupons through courier/post or suggest online print.\n" +
                    "20.8. There is no refund if coupons are delivered or emailed. Customer can demand courier tracking number or resend of email with links of coupons. Refund is given in case of non-receive of offer coupons or other company acceptable conditions.\n" +
                    "20.9. Free paid service offers are at selected workshops. Please check on our website or the app or by calling or asking for a list of workshops offering the same. Free Paid service means free labour charges, it does not include any consumable.\n" +
                    "20.10. It is not necessary that all mentioned services in the brochure are available at one workshop, some services are specialized and available at few workshops. Please check availability and workshops before buying offer.\n" +
                    "20.11. Mekvahan NEVER sell coupons in the retail market to avoid mis-use of it, those coupons are sent through courier/post or send through online.\n" +
                    "20.12. Mekvahan will not take any guarantee to serve customers if they buy any offer from duplicate, pirated sources. Few agencies duplicate offers and try to sell in the market. Customer must call Mekvahan helpdesk before buying any offer.\n"));
            list.add(new StaticData(1,"21. Do-It-Yourself and Motor Vehicle Care Tips or Articles\n"));
            list.add(new StaticData(2,"The information contained in car/bike care articles, tips, do-it-yourself guide, is for knowledge and educational purposes only and cannot substitute for the advice of professional car/bike / auto mechanic or authorized car/bike workshop or dealer or service station. Please don't attempt to service your car/bike if you don't have proper knowledge and tools, you can be injured and your vehicle could be damaged. Take your car/bike to a dealer or a repair shop.\n"));
            list.add(new StaticData(1,"Dealer Grading / Ranking / Reviews\n"));
            list.add(new StaticData(2,"To make system transparent for customers/ visitors, Mekvahan ranks Service vendors/service centres/repair shops based on a formula which is based on two criteria: \n" +
                    "1. Customer satisfaction.\n2. Aggression/communication/Conversion. The dealer who has more customer satisfaction and aggression to serve customer will get a higher rank than others. Formula for ranking may change as we get more analysis to provide customer more transparency for the right workshop or service. There is no guarantee for accuracy of the ranking but Mekvahan would help customers to meet the best with transparent system.\n"));
            list.add(new StaticData(1,"Privileged Customer Program\n"));
            list.add(new StaticData(2,"Loyal/Privileged Customer Membership program is launched to provide more reliability and help customers with Mekvahan services as one-stop-shop concept. Please read all terms and conditions and service offered in the Mekvahan brochure.\n"));
            list.add(new StaticData(1,"22. Bounced Emails / Problem with emails / spam mails.\n"));
            list.add(new StaticData(2,"Mekvahan technical team tries to maintain email hosting space, but in rare cases because of huge responses email storage or other unseen circumstances, email hosting server may behave differently because of any reason like shortage of space etc. Any customer can get some bounced email messages because of full of hosting emails storage or any other reason. Please inform us for any unsubscribe issue or if you are receiving Mekvahan email as spam. Mekvahan team has no intention to send spam mails. We will take immediate action to stop spam. Spamming may be because of any technical reason, your email box may not be considering Mekvahan.com host as genuine even if this is hosted at reputed IP and host. Since email technology varies from company to company, so it cannot be controlled. Some email box shows one email coming from genuine but same email in another mailbox can be a spam.\n" +
                    "To receive emails into your email box as non-spam, please send feedback to your Email Service Provider with tag spam and non-spam (as per their rules to tag them).\n" +
                    "Unwanted mails\n" +
                    "Mekvahan.com technical team use secure standard code and caches to prevent hacking or mis-use of web form and update code from time-to-time with recent technology. In case of receipt of any unwanted mail from Mekvahan domain, it should be informed with a URL of the page to help us preventing mis-use of web forms.\n"));
            list.add(new StaticData(1,"23. REFUND POLICY\n"));
            list.add(new StaticData(2,"Cancellation/Refund policy :- Mekvahan believes in helping its customers as far as possible, and has therefore a liberal cancellation/refund policy.\n" +
                    "\n"));
            list.add(new StaticData(1,"Cancellation Policy:-\n"));
            list.add(new StaticData(1,"Under this policy:\n"));
            list.add(new StaticData(2,"● Cancellation request can be made via the Mekvahan Mobile Application to make a booking or via call or e-mail to care@mekvahan.com.\n" +
                    "● Cancellations will be considered only if the request is made before the car has been\n" +
                    "picked up by the Mekvahan’s on-field Executive.\n" +
                    "● In case pickup/drop service is availed, a cancellation fee of INR 99.00 will be charged\n" +
                    "once the cancellation request is accepted by Mekvahan.\n" +
                    "● Cancellation request will not be entertained if the orders have been communicated to the vendors/merchants and they have initiated the services. However, in case you have\n" +
                    "agreed to your booking and service is being provided to your car and you choose to\n" +
                    "cancel while the service is still in progress, you have to pay Mekvahan or it’s partnered\n" +
                    "workshop the total amount for the service done.\n" +
                    "● In case of cancellation is made via phone call, cancellation is to be deemed accepted by\n" +
                    "Mekvahan only if the user receives a confirmation email/sms on the registered email\n" +
                    "id/phone number. In case the e-mail/sms is not received by user, booking will stand\n" +
                    "Confirmed.\n"));
            list.add(new StaticData(1,"Refund Policy\n" +
                    "\n" +
                    "Under this policy:\n"));
            list.add(new StaticData(2,"● Refund can be claimed only after service order cancellation.\n" +
                    "● Refund will be made only if the cancellation is done before initiating the services by the Service Station.\n" +
                    "● In case you feel that the service received is not satisfactory or is not as per your\n" +
                    "expectations, you must bring it to the notice of our customer service by dropping a mail\n" +
                    "to care@mekvahan.com within 24 hours of receiving the service. The Customer Service\n" +
                    "Team after looking into your complaint will take an appropriate decision.\n" +
                    "● Once a job is completed we will not accept any return or refund request. However\n" +
                    "there may be certain circumstances where if we feel upon investigation that the service\n" +
                    "provided was faulty or not up to the standard we may accept partial or complete refund\n" +
                    "of the service/repair job amount. The nature and acceptance of refund remains on sole\n" +
                    "discretion of Mekvahan.\n" +
                    "● In case of complaints regarding service parts that come with a warranty from\n" +
                    "manufacturers, please refer the issue to the concerned Manufacturing Company.\n"));
            list.add(new StaticData(1,"Prepaid Service Cancellation Refund (if applicable)\n"));
            list.add(new StaticData(2,"● In case of the cancellation of the service if the payment was prepaid and the pickup was not done, we will not charge any cancellation charges and we will refund your money.\n" +
                    "● If you haven’t received a refund yet, first check your bank account again. Then contact\n" +
                    "your credit card company, it may take some time before your refund is officially\n" +
                    "posted. Next contact your bank.\n" +
                    "● There is often some processing time before a refund is posted. If you’ve done all of this\n" +
                    "and you still have not received your refund yet, please contact us at care@mekvahan.com\n" +
                    "\n" +
                    "1. No Refund Policy Cases (Applicable to all services contracts)\n" +
                    "2. EXPIRY Contract.\n" +
                    "3. Non Receive of Application through proper channel (email/post)\n" +
                    "4. Receive of Some Services or Deliverables.\n" +
                    "5. Pressurising staff/employee in a wrong way.\n" +
                    "6. Defame company without prior request to company.\n" +
                    "\n"));
            list.add(new StaticData(1,"FOR DEALER/WORKSHOP/OUTLET\n"));
            list.add(new StaticData(2,"1. No refund for EXPIRY contract.\n" +
                    "2. Forcing/pushing company or individual (by wrong way of pressure/force) for refund is considered to be illegal and No refund will be claimed if proven wrong way of pressure on any associate / employee/staff.\n" +
                    "3. Mekvahan.com is promotion/marketing company and do not guarantee for any amount of business until specified in the contract.\n" +
                    "4. Dealer/Workshop/merchant/outlets can request for refund using proper channel of the company/process and cannot force/push/pressure/coerce anybody for refund.\n"));
            list.add(new StaticData(1,"However, Mekvahan does not guarantee any refund, and it merely facilitates.\n"));
            list.add(new StaticData(1,"FOR CUSTOMER MEMBERSHIPS\n"));
            list.add(new StaticData(2,"1. Customer memberships includes services provided by Merchant/Workshop/Dealer/Outlet so Mekvahan can help customers servicing them, but cannot take guarantee for their quality of services.\n" +
                    "2. Customer can ask for refund in case of SERVICES not been offered.\n" +
                    "3. Once all memberships coupons/cards/material sent to customer or delivered, customer cannot ask for refund until they have not been serviced by merchant/members/workshop/outlet.\n" +
                    "4. In case of problems with quality of workshop service, Mekvahan.com can mediate dispute but the customer cannot claim for any refund or settlement from Mekvahan.com. \n" +
                    "5. Mekvahan.com is doing regular effort to bring BEST merchant/workshops/outlet on one platform as a concept, but customer members need to check themselves if merchant/workshop/outlet is serving as per commitment. Mekvahan.com ask for feedback and try to resolve dispute (with mutual consent from both parties-customer and workshop), but cannot take guarantee for every service.\n"));
            list.add(new StaticData(1,"FOR EXCLUSIVE PARTNERSHIP / LONG TERM CONTRACT TERMINATION\n"));
            list.add(new StaticData(2,"1. Exclusive Contract / Corporate company can request for refund using proper channel of the company/process.\n" +
                    "2. Refund will be paid after adjustment of work done by Mekvahan.com team.\n" +
                    "3. Refund shall be requested within 3 months’ notice period of Termination of the contract, as stated in the contract.\n"));
            list.add(new StaticData(2,"Minimum 3 months shall be considered for refund.\n" +
                    "1. Termination letter shall be written officially and duly signed by the authority of the company.\n" +
                    "2. Forcing/pushing company or individual (by wrong way of pressure/force) for refund is considered to be illegal.\n" +
                    "\nNOTE: We update these terms from time-to-time, regularly and all customers/visitors at Mekvahan agrees to read it when required and company will not inform separately for any updates of these Terms and Conditions.\n"));


//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));
//            list.add(new StaticData(2,""));
//            list.add(new StaticData(1,""));


        }

        else if(type.equals(TERMS_OF_USE)){
            list.add(new StaticData(1,"1. Background\n"));
            list.add(new StaticData(2,"1. This document is an electronic record in terms of (i) Information Technology Act, 2000; (ii) the rules framed there under as applicable; and (iii) the amended provisions pertaining to electronic records in various statutes as amended by the Information Technology Act, 2000. This electronic record is generated by a computer system and does not require any physical or digital signatures.\n" +
                    "2. This document is published in accordance with the provisions of Rule 3 (1) of the Information Technology (Intermediaries guidelines) Rules, 2011 that require publishing the rules and regulations, privacy policy and Terms of Use for access or usage of the Platform (defined below).\n" +
                    "3. Mekvahan (“Web application/Website or app”) and http://mekvahan.com/ (“Website”) together referred to as “Platform”, is owned, registered and operated by UDYAMAT MA VIRAMASVA PVT LTD (Company), a private limited company, incorporated under the provisions of the Companies Act, 2013, and having its principal place of business at Aya Nagar, Delhi.\n" +
                    "4. These terms of usage (“Terms of Use”) govern your use of the Platform and Services (as defined below) as aggregated through the Platform. By using or visiting our Platform, or by using any Services aggregated through the Platform, You shall be deemed to have read, understood and accepted to be bound by these Terms of Use.\n" +
                    "5. For the purpose of these Terms of Use, wherever the context so requires “Your” or “User” shall mean any natural person who uses the Platform for availing the Services. The term “We”, “Us”, “Our” shall mean the Company, its employees, and authorised agents that perform any services on the Company’s behalf.\n" +
                    "6. We reserve the right to make changes to these Terms of Use at any time. Any such modifications will become effective immediately upon posting to the Platform and your continued use of the Platform, and/or the Services aggregated through the Platform constitutes your agreement to such modifications. You agree to periodically review the current version of these Terms of Use as posted on the Platform.\n"));
            list.add(new StaticData(1,"2. Services\n"));
            list.add(new StaticData(2,"1. We are service aggregators and through our Platform, We enable the User(s) to receive car/bike related repairs, services and maintenance and also roadside assistance including quick check-up, key lock- out, car/bike servicing etc. (“Services”), from third party workshops and their authorized personnel (“Service Provider”), under agreement with Us on a real time basis. These Services are made available to You for Your personal, non-commercial use.\n" +
                    "2. For the purposes of these Terms of Use,\n" +
                    "   a. “Applicable Law” shall mean any statute, law, regulation, ordinance, rule, judgment, notification, rule of common law, order, decree, bye-law, government approval, directive, guidelines, requirements or other governmental restriction, or any similar form of decision of, or determination by, or any interpretation, policy or administration, having the force of law of any of the foregoing, by any Authority having jurisdiction over the matter in question, whether in effect as of the date of these Terms of Use or thereafter.\n" +
                    "   b. “Authority” shall mean any national, state, provincial, local or similar government, governmental, regulatory or administrative authority, branch, agency, any statutory body or commission or any non-governmental regulatory or administrative authority, body or other organization to the extent that the rules, regulations and standards, requirements, procedures or orders of such authority, body or other organizations that have the force of Applicable Law or any court, tribunal, arbitral or judicial body in India.\n" +
                    "   c. “Minimum Age” shall mean any person aged 18 (Eighteen) years and above.\n" +
                    "2. In order to avail the Services aggregated through the Platform, the User(s) must download the Mobile App and mark the location at which they would like the Service Provider to arrive and provide the requisite Service.\n" +
                    "3. The User(s) shall then be required to select the Service they would like to be provided by the Service Provider (“Service Request”), register themselves and their vehicle on the Platform, and verify their registration.\n" +
                    "4. You agree, inter alia, to provide true, accurate, current and complete information about yourself and the vehicle, as prompted by the Platform registration form. If you provide any information that is untrue, inaccurate, not current or incomplete or We have reasonable grounds to suspect that such information is untrue, inaccurate, not current, incomplete or that You are misusing the Platform in any manner, We reserve the right to indefinitely suspend, terminate or block access to the Platform and the Services aggregated through the Platform.\n" +
                    "5. The User(s) shall then be required to ‘confirm’ their Service Request, and upon such confirmation of the Service Request by the User and acceptance of the same by the Service Provider, the Platform shall provide the details of the Service Provider to the User on a real time basis.\n" +
                    "6. Cancellation of Service Request: The User(s) can cancel a Service Request within 5 (five) minutes of making such a Service Request, on the Platform. In the event the User wishes to cancel a Service Request raised, after the expiry of 5 (five) minutes as mentioned above, the User shall be required to call on the +91 7838878899 to cancel such Service Request. Further, if a User cancels 3 (three) or more Service Requests by calling the customer care number as provided herein, We reserve the right to indefinitely suspend, terminate or block access of such User to the Platform and the Services aggregated through the Platform.\n"));
            list.add(new StaticData(1,"2. Platform to connect the Service Providers and Users"));
            list.add(new StaticData(2,"1. We do not provide any Services under these Terms of Use, We merely act as a facilitator/aggregator between the Service Providers and the Users, for availing the Services through the Service Provider. These Terms of Use should not be construed in any way to mean that We provide such Services herein.\n" +
                    "2. We do not make any representation or warranty as to the quality of the Services to be provided by the Service Provider to the User. We do not accept liability for any errors or omissions committed by the Service Provider or their authorised personnel.\n" +
                    "3. We are not responsible for any inadequate performance or non-performance of the Services by the Service Provider. We shall not be required to mediate or resolve any dispute or disagreement between Service Provider(s) and the User(s).\n" +
                    "4. The Service Providers are not employees or agents of the Company and the Company shall not be held liable for their actions or inactions.\n"));
            list.add(new StaticData(1,"3. Representations, Warranties and Obligations of the Platform\n"));
            list.add(new StaticData(2,"1. We hereby represent and warrant that:\n" +
                    "a. We are a duly registered company as under the relevant provisions of the Companies Act, 2013;\n" +
                    "b. We are in compliance with the Applicable Law to provide Services, subject to these Terms of Use;\n" +
                    "c. We will comply with the Privacy Policy and ensure data security of the Users at all times.\n"));
            list.add(new StaticData(1,"2. Representations, Warranties and Obligations of the User\n"));
            list.add(new StaticData(2,"1. You hereby represent and warrant that:\n" +
                    "a. You are a natural person, of the Minimum Age, competent to contract, have read, understood and agree to be bound by these Terms of Use;\n" +
                    "b. You shall comply with and fully adhere to these Terms of Use;\n" +
                    "c. You shall provide accurate information and details about yourself and the vehicle, when prompted by the Platform registration form;\n" +
                    "d. You shall fulfill Your payment obligations for availing the Services through the Platform.\n"));
            list.add(new StaticData(1,"Intellectual Property Rights\n"));
            list.add(new StaticData(2,"1.Copyright\n" +
                    "   1.All content included on the Platform, including but not limited to, text, graphics, logos, designs, photographs, button icons, images, video clips, digital downloads, data compilations etc.is Our property and is protected by the Applicable Laws with respect to intellectual property rights. We reserve the right to terminate Your engagement with Us, if We, in Our sole and absolute discretion, believe that You are in violation of this clause.\n" +
                    "   2. The content made available on or via the Platform, is provided to You ‘AS IS’ for Your information and personal use only and may not be used, copied, reproduced, distributed, transmitted, broadcast, displayed, sold, licensed, or otherwise exploited for any other purpose whatsoever without Our prior written consent. We reserve all rights not expressly granted in and to the Platform.\n" +
                    "2. Trademarks\n" +
                    "   1. http://mekvahan.com/ is the domain of the Company. The Platform, including, but not limited to its graphics, logos, page headers, button icons, scripts and service names constitute trade dress of the Company. The trademarks, domain names and trade dress of the Company shall not be used or reproduced without prior written approval from the Company, and may not be used in connection with any product or service that is not affiliated with the Company.\n" +
                    "3. Links\n" +
                    "   1. The Platform may contain links to other websites (Linked Sites). The Linked Sites are not under the control of the Company. We are not responsible for the content of any Linked Site, including, without limitation to, any link contained in a Linked Site, or any changes or updates to a Linked Site.\n" +
                    "   2. We are not responsible for any form of transmission, whatsoever, received by the User from any Linked Site. We are providing these links only for convenience, and the inclusion of any such link does not imply endorsement by the Company, of the Linked Sites or any association with its operators or owners including the legal heirs or assigns thereof.\n" +
                    "   3. On accessing the Linked Sites, You shall be governed by the terms of use, privacy policy and such other additional policies of the Linked Sites. You further acknowledge and agree that We shall not be responsible or liable, directly or indirectly, for any damage or loss caused or alleged to be caused by or in connection with the use of or reliance on any such content, advertising, products, services or other materials available on or through any Linked Sites or for any errors, defamatory content, libel, slander, omissions, falsehoods, obscene content, pornographic material, or any profanity contained therein.\n" +
                    "4. Advertisement Links\n" +
                    "   1. The Platform may contain links to various advertisements (“Advertisement Site”). The Advertisement Site is not under the control of the Company. We are not responsible for the content on any Advertisement Site, including, without limitation to, any link contained in the Advertisement Site, or any changes or updates to the same.\n" +
                    "   2. These Advertisement Site(s) shall be construed to be Linked Sites (as mentioned above) and the provisions of Clause 7 above shall apply for Advertisement Site(s).\n" +
                    "5. User feedback/comments/ suggestions\n" +
                    "   1. While rating/ submitting/ posting comments/ suggestions/ opinions/ feedback etc. (“User Feedback”), the User agrees and acknowledges that:\n" +
                    "   a. The User Feedback does not contain any confidential information or is not in violation of any third party rights including intellectual property rights;\n" +
                    "   b. The User Feedback shall not be unlawful, obscene, defamatory, libelous, threatening, pornographic, harassing, hateful, racially or ethnically offensive, or is otherwise inappropriate;\n" +
                    "   c. We are not under any obligation of confidentiality, express or implied, regarding the User Feedback;\n" +
                    "   d. We reserve the right to use or disclose such User Feedback for any purpose, in any way, as We deem fit;\n" +
                    "   e. By posting/ submitting User Feedback, the same shall become our intellectual property right without any obligations including but not limited to any compensation or consideration, express or implied to You.\n"+
                    "6. Payment Terms\n" +
                    "   1. User(s) can may make payment through one of the following available options, for availing the Services through the Platform (“Service Fee”):\n" +
                    "       a. Internet Banking;\n" +
                    "       b. Credit/ Debit Card;\n" +
                    "       c. Paytm Wallet; or\n" +
                    "       d. Cash Payments.\n" +
                    "   2. The User agrees and accepts that the costs for spare parts shall be payable separately, over and above the Service Fee (“Spare Part Cost”). The Service Provider shall provide a receipt for such Spare Part Costs, at actuals.\n" +
                    "   3. The User agrees and accepts that all nuances and modalities relating to making payment using Internet Banking/ Debit/Credit Cards/ Paytm Wallet (“Virtual Payment Mode”) shall be separately governed by arrangement(s) / terms and conditions between You and the relevant banks/ Paytm Wallet. We shall not be responsible, in any manner whatsoever, for any liability that may arise in relation to the Virtual Payment Modes (including any fraudulent transaction).\n" +
                    "   4. While availing any of the payment method(s) available on the Platform, We will not be responsible or assume for any liability, whatsoever in respect of any loss or damage arising directly or indirectly to You due to (a) Lack of authorization for any transactions; (b) Any payment issues arising out of the transaction or (c) Decline of such transaction for any reason.\n" +
                    "   5. You understand, accept and agree that the payment facility provided by Us, is neither a banking nor financial service but is merely a facility for providing an automated online electronic payment, collection and remittance for the transactions between the Users and the Service Providers, on the Platform using the existing authorized banking infrastructure and payment gateway networks. Further, by providing a payment facility, We are neither acting as trustees nor acting in any fiduciary capacity with respect to the payments made by the User for availing the Services on the Platform.\n" +
                    "   6. We reserve the right to refuse to process any request for Service availed by a User with a prior history of questionable charges including without limitation, breach of any agreements by such User with Us, or breach/violation of any Applicable Law.\n" +
                    "   7. GST or any other applicable taxes shall be borne by the User.\n"+
                    "7. Privacy\n" +
                    "   1. We collect, store, process and use Your information in accordance with Our Privacy Policy. By using the Platform and/ or by providing Your information, You consent to the collection and use of the information You disclose on the Platform in accordance with Our Privacy Policy.\n" +
                    "8. Fraud and Improper Conduct\n" +
                    "   1. You may only access the Platform and use the Services for lawful purposes. You are solely responsible for the knowledge of and adherence to any and all provisions of Applicable Law pertaining to Your use of the Services. You agree that You will not in any way:\n"));
            list.add(new StaticData(2,"     a. Interfere with the ability of others to access or use the Platform and the Services aggregated through the Platform;\n" +
                    "       b. Disrupt the normal flow of communication or otherwise act in a manner that adversely affects other Users' ability to use the Platform or the Services;\n" +
                    "       c. Interfere with or disrupt the Services or servers or networks connected to the Services, or disobey any requirements, procedures, policies, or regulations of networks connected to the Services;\n" +
                    "       d. Upload or post or transfer, any content or other material that contains or constitutes viruses, Trojan horse or other code with malicious, disruptive and/or destructive features;\n" +
                    "       e. You shall not attempt to interfere with any other User’s use of the Platform or the Services aggregated through the Platform;\n" +
                    "       f. You shall not use any false or misleading information (e.g., false or misleading names, email addresses or URLs) when using the Service, including, without limitation, with respect to any identifying information about yourself, and all the information that You provide must be accurate and correct.\n"));
            list.add(new StaticData(1,"2. Limitation and Disclaimer of Warranty\n"));
            list.add(new StaticData(2,"1. The Platform, the Services and each portion thereof are provided AS IS without warranties of any kind either expressed or implied. To the fullest extent possible pursuant to Applicable Law, We disclaim all warranties, express or implied, with respect to the Platform, the Services and each portion thereof, including, but not limited to, non-infringement or other violation of intellectual property rights.\n" +
                    "2. We do not warrant or make any representations regarding the use, validity, accuracy, or reliability of the Platform.\n" +
                    "3. We do not warrant or make any representations that the Platform shall (i) meet Your requirements or reliable; (ii) be uninterrupted, timely, secure or error-free; (iii) rectify any errors found on the Platform’s software. Further, access to the Platform shall be contingent to Your internet accessibility and We shall not be held liable for any lack/ sporadic breaks in Your internet accessibility.\n" +
                    "4. We shall not be liable in the event any damage or loss occurs to your computer system, or any other electronic device, or any data as a result of downloading the Mobile App or visiting the Website.\n" +
                    "5. We do not warrant or make any representations regarding the reliability, suitability or quality of the Service provided by the Service Provider.\n" +
                    "6. We shall not be liable for any loss or damages suffered by the User due to performance or non-performance of the Services by the Service Provider. Further, We shall not be liable for any loss or damage suffered, on account of any fault, willful misconduct or negligence on the part of the Service Provider\n" +
                    "7. We shall not be liable for any direct, indirect, incidental or consequential damages whatsoever incurred by the User due to use of the Services or due to the non- availability of the Platform or the Services.\n" +
                    "8. If You are dissatisfied or harmed by the Platform or anything related with the Platform, Your sole remedy shall be to terminate these Terms of Use by uninstalling the Mobile App or by leaving the Website.\n" +
                    "9. We shall be entitled to disclose to the Authority, as required by Applicable Law or by any directive or request from any government body, the particulars of the User engaged with the Platform.\n" +
                    "10. We shall be entitled to add, to vary or amend any or all these terms and conditions at any time and the User shall be bound by such addition, variation or amendment once such addition, variation or amendment is incorporated into these terms and conditions, and such amended terms and conditions are published on the Platform.\n"));
            list.add(new StaticData(1,"3. Indemnification\n"));
            list.add(new StaticData(2,"1. You undertake to indemnify Us, for any losses or damages resulting from any third party claims or complaints arising from, or in connection with Your actions on the Platform, and/or breach of these Terms of Use.\n"));
            list.add(new StaticData(1,"4. Termination\n"));
            list.add(new StaticData(2,"1. We may terminate these Terms of Use with respect to You, immediately without notice in the event of any breach by You of these Terms of Use or any of our applicable policies, as posted on the Platform from time to time or upon a misuse of the Services by You.\n" +
                    "2. You agree that upon the termination of these Terms of Use, We may delete all information related to You with respect to the Services availed by you. Further, You will no longer be able to access the Platform.\n"));
            list.add(new StaticData(1,"5. Severability\n"));
            list.add(new StaticData(2,"1. If any part of the Terms of Use are determined to be invalid or unenforceable pursuant to Applicable Laws, including, but not limited to, the warranty disclaimers and liability limitations set forth above, then the invalid or unenforceable provision will be deemed to be superseded by a valid, enforceable provision that most closely matches the intent of the original provision and the remainder of the Terms of Use for Services shall continue in effect.\n"));
            list.add(new StaticData(1,"6. General\n"));
            list.add(new StaticData(2,"1. Unless otherwise specified herein, these Terms of Use for Services aggregated through the Platform and the Privacy Policy (provided on the Platform) constitutes the entire agreement between you and the Platform, in respect of the Services and supersedes all previous written and oral agreements between You and the Platform, if any. Our failure to act with respect to a breach by you or others does not waive Our right to act with respect to subsequent or similar breaches.\n"));
            list.add(new StaticData(1,"7. Governing Law\n"));
            list.add(new StaticData(2,"1. Terms of Use shall be governed by and constructed in accordance with the Applicable Law without reference to conflict of laws and principles. The courts in New Delhi, shall have the exclusive jurisdiction to determine any disputes arising in relation to, or under, these Terms of Use.\n"));
            list.add(new StaticData(1,"8. Grievance Officer\n"));
            list.add(new StaticData(2,"1. In accordance with Information Technology Act 2000 and rules made there under, name and contact details of the Grievance Officer are provided below:\n"));
            list.add(new StaticData(1,"Attention: Sanjeev Maurya, Operations Manager\n" +
                    "Email: coo@mekvahan.com\n"));




        }

        else if(type.equals(PRIVACY_POLICY)){

            list.add(new StaticData(1,"1. Background\n"));
            list.add(new StaticData(2,"1. This document is an electronic record in terms of (i) Information Technology Act, 2000; (ii) the rules framed their under as applicable; and (iii) the amended provisions pertaining to electronic records in various statutes as amended by the Information Technology Act, 2000. This electronic record is generated by a computer system and does not require any physical or digital signatures.\n" +
                    "2. This Privacy Policy (“Privacy Policy”) is published in accordance with: (i) Section 43A of the Information Technology Act, 2000; (ii) Regulation 4 of the Information Technology (Reasonable security practices and procedures and sensitive personal information) Rules, 2011 (“SPI Rules”); and (iii) Regulation 3(1) of the Information Technology (Intermediaries Guidelines) Rules, 2011, that require publishing the privacy policy that is applicable in relation to the access or usage of mekvahan (“Web application/Website”) and http://mekvahan.com/(“Website”) together referred to as “Platform”.\n" +
                    "3. The Platform is owned, registered and operated by Udyamat Ma Viramasva Private Limited (Company), a private limited company, incorporated under the provisions of the Companies Act, 2013, and having its principal place of business at Aya Nagar, delhi.\n" +
                    "4. This Privacy Policy covers: (i) the type of information collected from the Registered Users (as defined below) through the Platform including sensitive personal data or information; (ii) the purpose, means and modes of usage of such information; and (iii) how and to whom such information which has been collected will be disclosed.\n" +
                    "5. For the purpose of this Privacy Policy, wherever the context so requires “Your” or “User” shall mean any natural person who uses the Website for availing Services. The terms “We”, “Us”, “Our” shall mean the Company, its employees, and authorised agents that perform any services on the Company’s behalf.\n" +
                    "6. We reserve the right to make changes to this Privacy Policy at any time. Any such modifications will become effective immediately upon posting to the Platform and your continued use of the Platform, and/or the Services constitutes your consent to such modifications. If you object to any changes, you may terminate your account. You agree to periodically review the current version of the Privacy Policy as posted on the Platform.\n"));
            list.add(new StaticData(1,"2. Services"));
            list.add(new StaticData(2,"1. We are service aggregators and through our Platform, we enable the User(s) to receive car related repairs, services and maintenance and also roadside assistance including quick check-up, key lock- out, car servicing etc.(”Services”), from third party workshops and their authorized personnel, under agreement with Us (“Service Provider”) on a real time basis. These Services are made available to You for Your personal, non-commercial use.\n" +
                    "2. All the other defined terms shall have the same meaning as provided for under the Terms of Use http://mekvahan.com/terms.php published on this Platform.\n"));
            list.add(new StaticData(1,"3. Type of information collected from the User(s)\n"));
            list.add(new StaticData(2,"1. For the purpose of this Privacy Policy, “personal information” as defined under Section 2(i) of the SPI Rules means, any information that relates to a natural person, which, either directly or indirectly, in combination with other information available or likely to be available with a body corporate, is capable of identifying such person.\n" +
                    "2. “Sensitive personal information” as defined under Section 3 of the SPI Rules means such personal information which consists of information relating to:\n" +
                    "\na. password;\n" +
                    "b. financial information such as Bank account or credit card or debit card or other payment instrument details;\n" +
                    "c. physical, physiological and mental health condition;\n" +
                    "d. sexual orientation;\n" +
                    "e. medical records and history;\n" +
                    "f. biometric information;\n" +
                    "g. any detail relating to the above clauses as provided to body corporate for providing service; and\n" +
                    "h. any of the information received under above clauses by body corporate for processing, stored or processed under lawful contract or otherwise\n" +
                    "\n2. We require Users who visit the Platform or avail our service through the Platform to provide us with the following information:\n" +
                    "a. Name of the User;\n" +
                    "b. E-mail Address;\n" +
                    "c. Phone Number;\n" +
                    "d. Credit card or debit card or any other such payment information which is required for payment purposes;\n" +
                    "e. Information pertaining to your vehicle;\n" +
                    "f. Other such information  may be required at the time of availing the Services through the Platform.\n"));
            list.add(new StaticData(1,"4. How the collected information will be used\n"));
            list.add(new StaticData(2,"1. The information collected by Us, shall be used in the manner described below:\n" +
                    "\na. User name: The user name shall be used to address the User and to customize the Services availed by the user through the Platform, and to personalize all communications that are sent to the User by Us.\n" +
                    "b. E-mail Address: Your Email address will be used to communicate:\n" +
                    "a. Service messages from time to time; and\n" +
                    "b. Feedback from the User regarding the Services provided by the Service Provider;\n" +
                    "c. Marketing emails; and\n" +
                    "d. Any other such communication with the User.\n" +
                    "2. Phone Number: Phone number will be an alternative method to contact the User, for any matter related to the access of the Platform or the Services through the Platform, including but not limited to sending promotional and transactional messages.\n" +
                    "3. Credit Card/Debit Card or any other such payment information: Credit Card/Debit Card or any other such payment information is used for charging the User for availing Services through the Platform.\n" +
                    "4. Vehicle Information: Vehicle Information shall be used to identify the vehicle and provide appropriate Services as requested by the User.\n" +
                    "5. In order to improve the quality of our Services, we may ask you to provide us with information regarding your experiences with the Service Provider and/or your experiences on the Platform. Users have the option of choosing not to provide us with this information.\n"));
            list.add(new StaticData(1,"5. Disclosure of Personal Information\n"));
            list.add(new StaticData(2,"1. All financial information that is collected through the Platform is used solely to enable the User to make payments for the Services availed through the Platform, and is not used for marketing or promotional purposes. Towards this end, we disclose such information to a third-party intermediary, for the sole purpose of processing the payments made by the User on the Platform.\n" +
                    "2. We may also disclose certain information to third parties solely to help diagnose technical problems, to administer the Platform, and improve the quality of the Services provided by us.\n" +
                    "3. We reserve the right to disclose any personal information as required by law and when we believe, at our sole discretion that disclosure is necessary to protect our rights, protect someone from injury and/or to comply with a judicial proceeding, court order, or legal process served on our Platform.\n"));
            list.add(new StaticData(1,"6. Cookies\n"));
            list.add(new StaticData(2,"1. The Platform uses temporary cookies, which are files that your web browser puts on your system when you visit a website, to store certain information that is not sensitive personal information. The information collected through these cookies is used by us for the technical administration of the Platform, research and development, and to improve the quality of our Platform.\n" +
                    "2. We may use third party cookies to track visitor behaviour and to improve the quality of our services. However, such cookies will not store any kind of personal information, nor will such information be disclosed to any third party.\n" +
                    "3. These cookies are intended to be automatically cleared or deleted when the User quits the browser application. You are encouraged to use the “clear cookies” functionality of your browser to ensure such clearing / deletion, since it is impossible for us to guarantee, predict or provide for the behaviour of your system.\n" +
                    "4. The information we collect with cookies is not sold, rented, or shared with any third parties, other than for internal Platform development and maintenance.\n"));
            list.add(new StaticData(1,"7. Third Party Links\n"));
            list.add(new StaticData(2,"1. We may provide links to websites for the convenience and information of Users. These websites may not be owned, controlled, or operated by Us. In those cases, We cannot control how information collected by those websites will be used, shared, or secured. If the User visits linked sites, We strongly recommend that the User reviews the privacy notices or policies posted at those sites. We are not responsible for the content of linked sites, the User’s use of them, or the information practices of their operators.\n"));
            list.add(new StaticData(1,"8. Advertisement Links\n"));
            list.add(new StaticData(2,"1. The Platform may contain links to various advertisements (“Advertisement Site”). The Advertisement Site is not under the control of the Platform. We are not responsible for the content on any Advertisement Site, including, without limitation to, any link contained in the Advertisement Site, or any changes or updates to the same.\n" +
                    "2. These Advertisement Site(s) shall be construed to be Linked Sites (as mentioned above) and the provisions of Clause 7 above shall apply for Advertisement Site(s)\n"));
            list.add(new StaticData(1,"8. Security Procedures\n"));
            list.add(new StaticData(2,"1. We have implemented industry standard security policies, rules and technical measures, to protect any kind of personal sensitive information that we have under our control from unauthorized access, improper use or disclosure, unauthorized modification and unlawful destruction or accidental loss.\n"));
            list.add(new StaticData(1,"9. Retention of Information\n"));

            list.add(new StaticData(2,"1. We retain any personal information that you may have provided to us, for as long as you engaged our services, and for a reasonable time thereafter.\n"));
            list.add(new StaticData(1,"10. Choice/Opt-Out\n"));
            list.add(new StaticData(2,"1. If the User wishes to opt- out of receiving non-essential (promotional, marketing-related) communications from Us, after setting up an account, they may choose to do so. Further, if a User wishes to remove all their contact information from the Platform, they can do so by opting out of the Platform and uninstalling the Platform."));
            list.add(new StaticData(1,"11. Governing Law\n"));
            list.add(new StaticData(2,"1. Privacy Policy shall be governed by and constructed in accordance with the Applicable Law without reference to conflict of laws and principles. The courts in New Delhi, shall have the exclusive jurisdiction to determine any disputes arising in relation to, or under, this Privacy Policy.\n"));

            list.add(new StaticData(1,"12. Grievance Officer\n"));
            list.add(new StaticData(2,"1. In accordance with Information Technology Act 2000 and rules made there under, name and contact details of the Grievance Officer are provided below:\n"));
            list.add(new StaticData(1,"Attention: Sanjeev maurya, Head of Operations.\n" +
                    "email: coo@mekvahan.com\n"));

        }

        else if(type.equals(DISCLAIMER)){

            list.add(new StaticData(2,"Service Plans / Charges are subject to revision by Mekvahan without prior notice.\n" +
                    "Mekvahan does not provide any Warranties / Guarantees or liabilities to its customers for services. No claim from vehicles owner side will be borne by Mekvahan or its members/employee/owner in any manner. Our services are provided at vehicle owners risk and he/she/it understands and accept the same at the time / before taking any services from us. A vehicle owner cannot claim for any warranties/guarantees in any manner. Mekvahan will be no more liable for any damage/discrepancy. Mekvahan will not be liable for any discrepancy in service as the customer is accepting services at its own risk. Mekvahan will also not be liable for the loss of any valuables in the vehicle.\n" +
                    "Mekvahan customer database and related information will not be shared with anyone. However, the data may be shared for statutory obligations.\n" +
                    "Mekvahan would use standard products for the cleaning process. Mekvahan has the right to add/delete/amend the terms and conditions above in the best interest of both the company and its customers.\n" +
                    "The customer is aware that in any circumstances the bill amount shall not be refunded. The same bill amount steam car wash facility shall be available to the Customer on the alternative day of his choice.   \n" +
                    "If the customer is not satisfied with services provided by MEKVAHAN, the customer should tell us about his complaint through our website www.mekvahan.com, we will try to respond to his dispute within 02 business days.\n" +
                    "\n" +
                    "Mekvahan reserves the right to cancel any scheme, for any reason at any time without notice.\n" +
                    "The information, software, products, and services included in or available through the Mekvahan  website may include inaccuracies or typographical errors. Changes are periodically added to the information herein Mekvahan and/or its suppliers may make improvements and/or changes in the Mekvahan website at any time. Advice received via the Mekvahan website should not be relied upon for personal, medical, legal or financial decisions and you should consult an appropriate professional for specific advice tailored to your situation. Mekvahan make no representations about the suitability, reliability, availability, timeliness and accuracy of the information, software, products, services and related graphics contained on the website for any purpose. If you are dissatisfied with any portion of the Mekvahan website, or with any of these terms of use, your sole and exclusive remedy is to discontinue using the website. \n" +
                    "\n" +
                    "Liability: Please be aware and understand that Mekvahan cannot be held responsible or liable for the pre-existing condition of your vehicle Or Damage occurred during wash due to wear and tear of the vehicle or by nature. MEKVAHAN cannot be held liable for any damage occurred  due to the certain condition that affected.( Such as heated Windshield may crack with wash, Scratches most often not visible before wash can be visible after wash and etc.) \n" +
                    "\n" +
                    "This includes, but is not limited to: pre-existing damage, alarm systems, oxidized or bad paint, loose moldings (any and all), windshields, windshield wipers, bug shields (as they age and crack), rear view mirrors with weak glue, antennas, batteries, any and all engine issues after cleaning, cloth or vinyl convertible tops.\n" +
                    "\n" +
                    "\n" +
                    "EXCEPT AS PROVIDED IN THE LIMITED WARRANTY, THE SERVICE AND PARTS ARE MADE AVAILABLE ON AN “AS IS,” “AS AVAILABLE,” AND “WITH ALL FAULTS” BASIS, WITHOUT ANY WARRANTIES OR CONDITIONS, EXPRESS, IMPLIED OR STATUTORY. YOU USE THE SERVICES ENTIRELY AT YOUR OWN RISK. WE DO NOT PROVIDE, AND SPECIFICALLY DISCLAIM, ANY REPRESENTATION OR WARRANTY OF ANY KIND TO YOU OR ANY OTHER PERSON OR ENTITY, INCLUDING, BUT NOT LIMITED TO, ANY EXPRESS OR IMPLIED warranties (I) OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE; (II) OF INFORMATIONAL CONTENT OR ACCURACY; (III) OF NON-INFRINGEMENT; (IV) OF QUIET ENJOYMENT; (V) OF TITLE; (VI) THAT THE MEKVAHAN  PLATFORM WILL OPERATE ERROR-FREE, OR IN AN UNINTERRUPTED FASHION; (VII) THAT ANY DEFECTS OR ERRORS IN THE  PLATFORM WILL BE CORRECTED; (VIII) THAT SERVICES OFFERED WILL BE AVAILABLE IN ALL MARKETS; OR (IX) THAT THE  PLATFORM IS COMPATIBLE WITH ANY PARTICULAR HARDWARE OR SOFTWARE. MEKVAHAN  MAKES NO REPRESENTATION, WARRANTY, OR GUARANTEE REGARDING THE RELIABILITY, TIMELINESS, QUALITY, SUITABILITY, OR AVAILABILITY OF THE SERVICES OR ANY SERVICES OR GOODS REQUESTED THROUGH THE USE OF THE SERVICES, OR THAT THE SERVICES WILL BE UNINTERRUPTED OR ERROR-FREE. MEKVAHAN  DOES NOT GUARANTEE THE QUALITY, SUITABILITY, SAFETY OR ABILITY OF THIRD PARTY PROVIDERS. YOU AGREE THAT THE ENTIRE RISK ARISING OUT OF YOUR USE OF THE SERVICES, AND ANY SERVICE OR GOOD REQUESTED IN CONNECTION THEREWITH, REMAINS SOLELY WITH YOU, TO THE MAXIMUM EXTENT PERMITTED UNDER APPLICABLE LAW. \n" +
                    "\n" +
                    "Limitations of Liability \n" +
                    "\n" +
                    "YOU AGREE NOT TO HOLD MEKVAHAN  (OR, ITS STOCKHOLDERS, AFFILIATES, LICENSORS, PARTNERS, MEMBERS, DIRECTORS, MANAGERS, OFFICERS, EMPLOYEES, AGENTS, SUCCESSORS, ASSIGNS AND CONTENT PROVIDERS (COLLECTIVELY, MEMBERS)) LIABLE FOR ANY DAMAGES, EXPENSES, LOSSES, SUITS, CLAIMS, AND/OR CONTROVERSIES THAT HAVE ARISEN OR MAY ARISE, WHETHER KNOWN OR UNKNOWN, RELATING TO YOUR USE OF OR INABILITY TO USE THE MEKVAHAN  PLATFORM OR ANY SERVICES, INCLUDING WITHOUT LIMITATION ANY LIABILITIES ARISING IN CONNECTION WITH THE CONDUCT, ACT, DELAY OF PERFORMANCE OR OMISSION OF ANY USER OR THIRD PARTY PROVIDER (OR ANY TRANSACTION OR RELATIONSHIP BETWEEN YOU AND ANY THIRD PARTY PROVIDER), ANY DISPUTE WITH ANY USER OR THIRD PARTY PROVIDER, ANY INSTRUCTION, ADVICE, ACT, OR SERVICE PROVIDED BY MEKVAHAN  OR MEMBERS, AND ANY DESTRUCTION OF YOUR INFORMATION. \n" +
                    "\n" +
                    "UNDER NO CIRCUMSTANCES WILL MEKVAHAN  OR MEMBERS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, CONSEQUENTIAL, SPECIAL, PUNITIVE OR EXEMPLARY DAMAGES, HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN AN ACTION FOR CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE) OR OTHERWISE, INCLUDING WITHOUT LIMITATION LOST PROFITS, LOST EARNINGS, LOST DATA, PERSONAL INJURY, OR PROPERTY DAMAGE, WHETHER OR NOT WE HAVE BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. MEKVAHAN  AND MEMBERS DO NOT ACCEPT ANY LIABILITY WITH RESPECT TO THE QUALITY OR FITNESS OF ANY WORK PERFORMED IN CONNECTION WITH THE MEKVAHAN  PLATFORM. \n" +
                    "\n" +
                    "THE MEKVAHAN  PLATFORM IS ONLY A VENUE FOR CONNECTING USERS. WE ARE NOT RESPONSIBLE FOR ASSESSING THE SUITABILITY, LEGALITY OR ABILITY OF ANY THIRD PARTY PROVIDER AND YOU EXPRESSLY WAIVE AND RELEASE MEKVAHAN  FROM ANY AND ALL LIABILITY, CLAIMS OR DAMAGES (ACTUAL, DIRECT OR CONSEQUENTIAL) OF EVERY KIND AND NATURE, KNOWN AND UNKNOWN, SUSPECTED AND UNSUSPECTED, DISCLOSED AND UNDISCLOSED, ARISING FROM OR IN ANY WAY RELATED TO ANY THIRD PARTY PROVIDER. WE EXPRESSLY DISCLAIM ANY LIABILITY THAT MAY ARISE BETWEEN YOU AND ANY THIRD PARTY PROVIDER. THE QUALITY OF THE SERVICES SCHEDULED OR REQUESTED THROUGH THE USE OF THE MEKVAHAN  PLATFORM IS ENTIRELY THE RESPONSIBILITY OF THE THIRD PARTY PROVIDER WHO ULTIMATELY PROVIDES SUCH SERVICES TO YOU. YOU UNDERSTAND, THEREFORE, THAT BY USING THE MEKVAHAN  PLATFORM, YOU MAY BE EXPOSED TO SERVICES THAT ARE POTENTIALLY DANGEROUS, OFFENSIVE, HARMFUL, UNSAFE OR OTHERWISE OBJECTIONABLE, AND THAT YOU USE THE MEKVAHAN  PLATFORM, AND THIRD PARTY PROVIDER, AT YOUR OWN RISK. NOTHING IN THIS AGREEMENT OR THE MEKVAHAN  PLATFORM CONSTITUTES, OR IS MEANT TO CONSTITUTE, ADVICE OF ANY KIND. IF YOU REQUIRE ADVICE IN RELATION TO ANY LEGAL, FINANCIAL OR MEDICAL MATTER YOU SHOULD CONSULT AN APPROPRIATE PROFESSIONAL. \n" +
                    "\n" +
                    "YOU ACKNOWLEDGE THAT THIRD PARTY PROVIDERS MAY NOT BE PROFESSIONALLY LICENSED OR PERMITTED. YOU AGREE THAT WE HAVE NO RESPONSIBILITY OR LIABILITY TO YOU RELATING TO VEHICLE SERVICES PROVIDED TO YOU BY THIRD PARTY PROVIDERS OTHER THAN AS EXPRESSLY SET FORTH IN THESE TERMS. \n" +
                    "\n" +
                    "SOME JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF CERTAIN TYPES OF DAMAGES, SO THE ABOVE limitation MAY NOT APPLY TO YOU. IF, NOTWITHSTANDING THE FOREGOING EXCLUSIONS, IT IS DETERMINED THAT MEKVAHAN  OR MEMBERS ARE LIABLE FOR DAMAGES, IN NO EVENT WILL THE AGGREGATE AND TOTAL LIABILITY, WHETHER ARISING IN CONTRACT, TORT, STRICT LIABILITY OR CAUSE OF ACTION, EXCEED THE AMOUNT INVOICED FOR THE ASSOCIATED JOB. \n" +
                    "\n" +
                    "BY USING THE (MEKVAHAN  PLATFORM) , YOU AGREE THAT THE EXCLUSIONS AND LIMITATIONS OF LIABILITY SET OUT IN THIS AGREEMENT ARE REASONABLE. IF YOU DO NOT BELIEVE THEY ARE REASONABLE, YOU MUST NOT USE THE  MEKVAHAN  PLATFORM.\n"));


        }

        else if(type.equals(GENERAL_QUERIES)){

            list.add(new StaticData(1,"1. Background\n"));
            list.add(new StaticData(2,"1. This document is an electronic record in terms of (i) Information Technology Act, 2000; (ii) the rules framed there under as applicable; and (iii) the amended provisions pertaining to electronic records in various statutes as amended by the Information Technology Act, 2000. This electronic record is generated by a computer system and does not require any physical or digital signatures.\n" +
                    "2. This document is published in accordance with the provisions of Rule 3 (1) of the Information Technology (Intermediaries guidelines) Rules, 2011 that require publishing the rules and regulations, privacy policy and Terms of Use for access or usage of the Platform (defined below).\n" +
                    "3. Mekvahan (“Web application/Website or app”) and http://mekvahan.com/ (“Website”) together referred to as “Platform”, is owned, registered and operated by UDYAMAT MA VIRAMASVA PVT LTD (Company), a private limited company, incorporated under the provisions of the Companies Act, 2013, and having its principal place of business at Aya Nagar, Delhi.\n" +
                    "4. These terms of usage (“Terms of Use”) govern your use of the Platform and Services (as defined below) as aggregated through the Platform. By using or visiting our Platform, or by using any Services aggregated through the Platform, You shall be deemed to have read, understood and accepted to be bound by these Terms of Use.\n" +
                    "5. For the purpose of these Terms of Use, wherever the context so requires “Your” or “User” shall mean any natural person who uses the Platform for availing the Services. The term “We”, “Us”, “Our” shall mean the Company, its employees, and authorised agents that perform any services on the Company’s behalf.\n" +
                    "6. We reserve the right to make changes to these Terms of Use at any time. Any such modifications will become effective immediately upon posting to the Platform and your continued use of the Platform, and/or the Services aggregated through the Platform constitutes your agreement to such modifications. You agree to periodically review the current version of these Terms of Use as posted on the Platform.\n"));
            list.add(new StaticData(1,"2. Services\n"));
            list.add(new StaticData(2,"1. We are service aggregators and through our Platform, We enable the User(s) to receive car/bike related repairs, services and maintenance and also roadside assistance including quick check-up, key lock- out, car/bike servicing etc. (“Services”), from third party workshops and their authorized personnel (“Service Provider”), under agreement with Us on a real time basis. These Services are made available to You for Your personal, non-commercial use.\n" +
                    "2. For the purposes of these Terms of Use,\n" +
                    "a. “Applicable Law” shall mean any statute, law, regulation, ordinance, rule, judgment, notification, rule of common law, order, decree, bye-law, government approval, directive, guidelines, requirements or other governmental restriction, or any similar form of decision of, or determination by, or any interpretation, policy or administration, having the force of law of any of the foregoing, by any Authority having jurisdiction over the matter in question, whether in effect as of the date of these Terms of Use or thereafter.\n" +
                    "b. “Authority” shall mean any national, state, provincial, local or similar government, governmental, regulatory or administrative authority, branch, agency, any statutory body or commission or any non-governmental regulatory or administrative authority, body or other organization to the extent that the rules, regulations and standards, requirements, procedures or orders of such authority, body or other organizations that have the force of Applicable Law or any court, tribunal, arbitral or judicial body in India.\n" +
                    "c. “Minimum Age” shall mean any person aged 18 (Eighteen) years and above.\n" +
                    "2. In order to avail the Services aggregated through the Platform, the User(s) must download the Mobile App and mark the location at which they would like the Service Provider to arrive and provide the requisite Service.\n" +
                    "3. The User(s) shall then be required to select the Service they would like to be provided by the Service Provider (“Service Request”), register themselves and their vehicle on the Platform, and verify their registration.\n" +
                    "4. You agree, inter alia, to provide true, accurate, current and complete information about yourself and the vehicle, as prompted by the Platform registration form. If you provide any information that is untrue, inaccurate, not current or incomplete or We have reasonable grounds to suspect that such information is untrue, inaccurate, not current, incomplete or that You are misusing the Platform in any manner, We reserve the right to indefinitely suspend, terminate or block access to the Platform and the Services aggregated through the Platform.\n" +
                    "5. The User(s) shall then be required to ‘confirm’ their Service Request, and upon such confirmation of the Service Request by the User and acceptance of the same by the Service Provider, the Platform shall provide the details of the Service Provider to the User on a real time basis.\n" +
                    "6. Cancellation of Service Request: The User(s) can cancel a Service Request within 5 (five) minutes of making such a Service Request, on the Platform. In the event the User wishes to cancel a Service Request raised, after the expiry of 5 (five) minutes as mentioned above, the User shall be required to call on the +91 7838878899 to cancel such Service Request. Further, if a User cancels 3 (three) or more Service Requests by calling the customer care number as provided herein, We reserve the right to indefinitely suspend, terminate or block access of such User to the Platform and the Services aggregated through the Platform.\n"));
            list.add(new StaticData(1,"3. Platform to connect the Service Providers and Users\n"));
            list.add(new StaticData(2,"1. We do not provide any Services under these Terms of Use, We merely act as a facilitator/aggregator between the Service Providers and the Users, for availing the Services through the Service Provider. These Terms of Use should not be construed in any way to mean that We provide such Services herein.\n" +
                    "2. We do not make any representation or warranty as to the quality of the Services to be provided by the Service Provider to the User. We do not accept liability for any errors or omissions committed by the Service Provider or their authorised personnel.\n" +
                    "3. We are not responsible for any inadequate performance or non-performance of the Services by the Service Provider. We shall not be required to mediate or resolve any dispute or disagreement between Service Provider(s) and the User(s).\n" +
                    "4. The Service Providers are not employees or agents of the Company and the Company shall not be held liable for their actions or inactions.\n"));
            list.add(new StaticData(1,"4. Representations, Warranties and Obligations of the Platform\n"));
            list.add(new StaticData(2,"1. We hereby represent and warrant that:\n" +
                    "a. We are a duly registered company as under the relevant provisions of the Companies Act, 2013;\n" +
                    "b. We are in compliance with the Applicable Law to provide Services, subject to these Terms of Use;\n" +
                    "c. We will comply with the Privacy Policy and ensure data security of the Users at all times.\n"));
            list.add(new StaticData(1,"5. Representations, Warranties and Obligations of the User\n\n"));
            list.add(new StaticData(2,"1. You hereby represent and warrant that:\n" +
                    "a. You are a natural person, of the Minimum Age, competent to contract, have read, understood and agree to be bound by these Terms of Use;\n" +
                    "b. You shall comply with and fully adhere to these Terms of Use;\n" +
                    "c. You shall provide accurate information and details about yourself and the vehicle, when prompted by the Platform registration form;\n" +
                    "d. You shall fulfill Your payment obligations for availing the Services through the Platform.\n"));
            list.add(new StaticData(1,"6. Intellectual Property Rights\n"));
            list.add(new StaticData(2,"1. Copyright\n" +
                    "2. All content included on the Platform, including but not limited to, text, graphics, logos, designs, photographs, button icons, images, video clips, digital downloads, data compilations etc.is Our property and is protected by the Applicable Laws with respect to intellectual property rights. We reserve the right to terminate Your engagement with Us, if We, in Our sole and absolute discretion, believe that You are in violation of this clause.\n" +
                    "3. The content made available on or via the Platform, is provided to You ‘AS IS’ for Your information and personal use only and may not be used, copied, reproduced, distributed, transmitted, broadcast, displayed, sold, licensed, or otherwise exploited for any other purpose whatsoever without Our prior written consent. We reserve all rights not expressly granted in and to the Platform.\n" +
                    "4. Trademarks\n" +
                    "5. http://mekvahan.com/ is the domain of the Company. The Platform, including, but not limited to its graphics, logos, page headers, button icons, scripts and service names constitute trade dress of the Company. The trademarks, domain names and trade dress of the Company shall not be used or reproduced without prior written approval from the Company, and may not be used in connection with any product or service that is not affiliated with the Company.\n"));

            list.add(new StaticData(1,"7. Links\n"));
            list.add(new StaticData(2,"1. The Platform may contain links to other websites (Linked Sites). The Linked Sites are not under the control of the Company. We are not responsible for the content of any Linked Site, including, without limitation to, any link contained in a Linked Site, or any changes or updates to a Linked Site.\n" +
                    "2. We are not responsible for any form of transmission, whatsoever, received by the User from any Linked Site. We are providing these links only for convenience, and the inclusion of any such link does not imply endorsement by the Company, of the Linked Sites or any association with its operators or owners including the legal heirs or assigns thereof.\n" +
                    "3. On accessing the Linked Sites, You shall be governed by the terms of use, privacy policy and such other additional policies of the Linked Sites. You further acknowledge and agree that We shall not be responsible or liable, directly or indirectly, for any damage or loss caused or alleged to be caused by or in connection with the use of or reliance on any such content, advertising, products, services or other materials available on or through any Linked Sites or for any errors, defamatory content, libel, slander, omissions, falsehoods, obscene content, pornographic material, or any profanity contained therein.\n"));
            list.add(new StaticData(1,"8. Advertisement Links\n"));
            list.add(new StaticData(2,"1. The Platform may contain links to various advertisements (“Advertisement Site”). The Advertisement Site is not under the control of the Company. We are not responsible for the content on any Advertisement Site, including, without limitation to, any link contained in the Advertisement Site, or any changes or updates to the same.\n" +
                    "2. These Advertisement Site(s) shall be construed to be Linked Sites (as mentioned above) and the provisions of Clause 7 above shall apply for Advertisement Site(s).\n"));
            list.add(new StaticData(1,"9. User feedback/comments/ suggestions\n"));
            list.add(new StaticData(2,"1. While rating/ submitting/ posting comments/ suggestions/ opinions/ feedback etc. (“User Feedback”), the User agrees and acknowledges that:\n" +
                    "a. The User Feedback does not contain any confidential information or is not in violation of any third party rights including intellectual property rights;\n" +
                    "b. The User Feedback shall not be unlawful, obscene, defamatory, libelous, threatening, pornographic, harassing, hateful, racially or ethnically offensive, or is otherwise inappropriate;\n" +
                    "c. We are not under any obligation of confidentiality, express or implied, regarding the User Feedback;\n" +
                    "d. We reserve the right to use or disclose such User Feedback for any purpose, in any way, as We deem fit;\n" +
                    "e. By posting/ submitting User Feedback, the same shall become our intellectual property right without any obligations including but not limited to any compensation or consideration, express or implied to You.\n"));
            list.add(new StaticData(1,"10. Payment Terms\n"));
            list.add(new StaticData(2, "1. User(s) can may make payment through one of the following available options, for availing the Services through the Platform (“Service Fee”):\n" +
                    "2. Internet Banking;\n" +
                    "   a. Credit/ Debit Card;\n" +
                    "   b. Paytm Wallet; or\n" +
                    "   c. Cash Payments.\n" +
                    "3. The User agrees and accepts that the costs for spare parts shall be payable separately, over and above the Service Fee (“Spare Part Cost”). The Service Provider shall provide a receipt for such Spare Part Costs, at actuals.\n" +
                    "4. The User agrees and accepts that all nuances and modalities relating to making payment using Internet Banking/ Debit/Credit Cards/ Paytm Wallet (“Virtual Payment Mode”) shall be separately governed by arrangement(s) / terms and conditions between You and the relevant banks/ Paytm Wallet. We shall not be responsible, in any manner whatsoever, for any liability that may arise in relation to the Virtual Payment Modes (including any fraudulent transaction).\n" +
                    "5. While availing any of the payment method(s) available on the Platform, We will not be responsible or assume for any liability, whatsoever in respect of any loss or damage arising directly or indirectly to You due to (a) Lack of authorization for any transactions; (b) Any payment issues arising out of the transaction or (c) Decline of such transaction for any reason.\n" +
                    "7. You understand, accept and agree that the payment facility provided by Us, is neither a banking nor financial service but is merely a facility for providing an automated online electronic payment, collection and remittance for the transactions between the Users and the Service Providers, on the Platform using the existing authorized banking infrastructure and payment gateway networks. Further, by providing a payment facility, We are neither acting as trustees nor acting in any fiduciary capacity with respect to the payments made by the User for availing the Services on the Platform.\n" +
                    "8. We reserve the right to refuse to process any request for Service availed by a User with a prior history of questionable charges including without limitation, breach of any agreements by such User with Us, or breach/violation of any Applicable Law.\n" +
                    "9. GST or any other applicable taxes shall be borne by the User.\n"));
            list.add(new StaticData(1,"11. Privacy\n"));
            list.add(new StaticData(2,"1. We collect, store, process and use Your information in accordance with Our Privacy Policy. By using the Platform and/ or by providing Your information, You consent to the collection and use of the information You disclose on the Platform in accordance with Our Privacy Policy.\n"));
            list.add(new StaticData(1,"12. Fraud and Improper Conduct\n\n"));
            list.add(new StaticData(2,"You may only access the Platform and use the Services for lawful purposes. You are solely responsible for the knowledge of and adherence to any and all provisions of Applicable Law pertaining to Your use of the Services. You agree that You will not in any way:\n"));
            list.add(new StaticData(2,"a. Interfere with the ability of others to access or use the Platform and the Services aggregated through the Platform;\n" +
                    "b. Disrupt the normal flow of communication or otherwise act in a manner that adversely affects other Users' ability to use the Platform or the Services;\n" +
                    "c. Interfere with or disrupt the Services or servers or networks connected to the Services, or disobey any requirements, procedures, policies, or regulations of networks connected to the Services;\n" +
                    "d. Upload or post or transfer, any content or other material that contains or constitutes viruses, Trojan horse or other code with malicious, disruptive and/or destructive features;\n" +
                    "e. You shall not attempt to interfere with any other User’s use of the Platform or the Services aggregated through the Platform;\n" +
                    "f. You shall not use any false or misleading information (e.g., false or misleading names, email addresses or URLs) when using the Service, including, without limitation, with respect to any identifying information about yourself, and all the information that You provide must be accurate and correct.\n"));
            list.add(new StaticData(1,"13. Limitation and Disclaimer of Warranty\n"));
            list.add(new StaticData(2,"1. The Platform, the Services and each portion thereof are provided AS IS without warranties of any kind either expressed or implied. To the fullest extent possible pursuant to Applicable Law, We disclaim all warranties, express or implied, with respect to the Platform, the Services and each portion thereof, including, but not limited to, non-infringement or other violation of intellectual property rights.\n" +
                    "2. We do not warrant or make any representations regarding the use, validity, accuracy, or reliability of the Platform.\n" +
                    "3. We do not warrant or make any representations that the Platform shall (i) meet Your requirements or reliable; (ii) be uninterrupted, timely, secure or error-free; (iii) rectify any errors found on the Platform’s software. Further, access to the Platform shall be contingent to Your internet accessibility and We shall not be held liable for any lack/ sporadic breaks in Your internet accessibility.\n" +
                    "4. We shall not be liable in the event any damage or loss occurs to your computer system, or any other electronic device, or any data as a result of downloading the Mobile App or visiting the Website.\n" +
                    "5. We do not warrant or make any representations regarding the reliability, suitability or quality of the Service provided by the Service Provider.\n" +
                    "6. We shall not be liable for any loss or damages suffered by the User due to performance or non-performance of the Services by the Service Provider. Further, We shall not be liable for any loss or damage suffered, on account of any fault, willful misconduct or negligence on the part of the Service Provider\n" +
                    "7. We shall not be liable for any direct, indirect, incidental or consequential damages whatsoever incurred by the User due to use of the Services or due to the non- availability of the Platform or the Services.\n" +
                    "8. If You are dissatisfied or harmed by the Platform or anything related with the Platform, Your sole remedy shall be to terminate these Terms of Use by uninstalling the Mobile App or by leaving the Website.\n" +
                    "9. We shall be entitled to disclose to the Authority, as required by Applicable Law or by any directive or request from any government body, the particulars of the User engaged with the Platform.\n" +
                    "10. We shall be entitled to add, to vary or amend any or all these terms and conditions at any time and the User shall be bound by such addition, variation or amendment once such addition, variation or amendment is incorporated into these terms and conditions, and such amended terms and conditions are published on the Platform.\n"));
            list.add(new StaticData(1,"14 Indemnification\n"));
            list.add(new StaticData(2,"1. You undertake to indemnify Us, for any losses or damages resulting from any third party claims or complaints arising from, or in connection with Your actions on the Platform, and/or breach of these Terms of Use.\n"));
            list.add(new StaticData(1,"15. Termination\n"));
            list.add(new StaticData(2,"1. We may terminate these Terms of Use with respect to You, immediately without notice in the event of any breach by You of these Terms of Use or any of our applicable policies, as posted on the Platform from time to time or upon a misuse of the Services by You.\n" +
                    "2. You agree that upon the termination of these Terms of Use, We may delete all information related to You with respect to the Services availed by you. Further, You will no longer be able to access the Platform.\n"));
            list.add(new StaticData(1,"16. Severability\n"));
            list.add(new StaticData(2,"1. If any part of the Terms of Use are determined to be invalid or unenforceable pursuant to Applicable Laws, including, but not limited to, the warranty disclaimers and liability limitations set forth above, then the invalid or unenforceable provision will be deemed to be superseded by a valid, enforceable provision that most closely matches the intent of the original provision and the remainder of the Terms of Use for Services shall continue in effect.\n"));
            list.add(new StaticData(1,"17. General\n"));
            list.add(new StaticData(2,"1. Unless otherwise specified herein, these Terms of Use for Services aggregated through the Platform and the Privacy Policy (provided on the Platform) constitutes the entire agreement between you and the Platform, in respect of the Services and supersedes all previous written and oral agreements between You and the Platform, if any. Our failure to act with respect to a breach by you or others does not waive Our right to act with respect to subsequent or similar breaches.\n"));
            list.add(new StaticData(1,"18. Governing Law\n"));
            list.add(new StaticData(2,"1. Terms of Use shall be governed by and constructed in accordance with the Applicable Law without reference to conflict of laws and principles. The courts in New Delhi, shall have the exclusive jurisdiction to determine any disputes arising in relation to, or under, these Terms of Use.\n"));
            list.add(new StaticData(1,"19. Grievance Officer\n"));
            list.add(new StaticData(2,"1. In accordance with Information Technology Act 2000 and rules made there under, name and contact details of the Grievance Officer are provided below:\n"));
            list.add(new StaticData(1,"Attention: Sanjeev Maurya, Operations Manager\n" +
                    "Email: coo@mekvahan.com\n"));

        }

        else if(type.equals(WALLET_FAQ)){

            list.add(new StaticData(1,"1. What’s new with Mekcoins & Mekcoins wallet?"));
            list.add(new StaticData(2,"We have made the Mekcoins rules quite simple and more beneficial for all the users! Now you can use flat" +
                    "Mekcoins of the transaction amount, instead of the other modes of payment. Earn much more Mekcoins" +
                    "than ever before and you can save more of the transaction value on every service of bike or car category," +
                    "basis the specific offer on the category. Mekcoins Earn & Burn is now more personalized to enhance your" +
                    "experience. If you have accumulated Mekcoins balance then Mekcoins burn gets prioritized for you to " +
                    "enjoy immediate benefit.\n"));

            list.add(new StaticData(1,"2. How Mekcoins works?"));
            list.add(new StaticData(2,"Mekcoins is a way of ensuring that you get a discount on each transaction. Mekcoins works on the same" +
                    "lines as cashback,Now you can use flat Mekcoins of the transaction amount, instead of the other modes" +
                    "of payment.\n"));

            list.add(new StaticData(1,"3. How do I earn SuperCash?"));
            list.add(new StaticData(2,"Offers/promo codes/ coupons can be used to earn Mekcoins, which is automatically credited in your" +
                    "wallet basis the specific T&C of the offer. You can add SuperCash with help of coupon codes on redeem" +
                    "voucher page.\n"));


            list.add(new StaticData(1,"4. Is there any minimum transaction limit to use Mekcoins?"));
            list.add(new StaticData(2,"There is no minimum transaction limit for any service payment on our app(like general service of car or" +
                    "bike, repairing, car care, etc).\n"));

            list.add(new StaticData(1,"5. Will my Mekcoins wallet amount expire?"));
            list.add(new StaticData(2,"Absolutely not! Mekcoins wallet amount, never expires.\n"));


            list.add(new StaticData(1,"6. Can I transfer SuperCash to other MobiKwik wallets or a bank account?"));
            list.add(new StaticData(2,"Sorry! You cannot transfer Mekcoins to another wallet or to a bank account. You can use it everywhere on" +
                    "Mekvahan for all transactions.\n"));

        }


        return  list;


    }


    //After successfull  booking
    public void holdUpTight(final String bookingId, final String date, final String lat, final String lng, final String type) {

        Log.e(TAG,"called : hold up tight");

        final Dialog dialog = new Dialog(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_hold_up_tight, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)mCtx).getWindowManager().getDefaultDisplay().getMetrics(metrics);


        float totalScreenWidth = metrics.widthPixels - 120f;
        Log.e(TAG,"totalWidth="+totalScreenWidth);


        ImageView iv_walker = view.findViewById(R.id.image_car);
        iv_walker.animate()
                .setDuration(1000)
                .translationX(totalScreenWidth);

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                launchParkingBookingSuccessDialog(bookingId, date, lat, lng, type);
            }
        }, 1000);
    }

    private void launchParkingBookingSuccessDialog(final String bookId, final String date, final String lat, final String lng, final String type) {

        final Dialog dialog = new Dialog(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_booking_success, null);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BillAnimation1;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                clearingStackAndMoveToHomePage();
            }
        });

        view.findViewById(R.id.track_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                clearStackAndTrackOrder(bookId, date, lat, lng, type);

            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                clearingStackAndMoveToHomePage();
            }
        });


        dialog.show();

    }

    private void clearStackAndTrackOrder(String bookId, String date, String lat, String lng, String type) {

        Intent i = new Intent(mCtx, TrackOrder2.class);


        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.putExtra("booking_id", bookId);
        i.putExtra("type", type);
        i.putExtra("booking_date", date);
        i.putExtra("lat", lat);
        i.putExtra("lng", lng);


        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mCtx.startActivity(i);
    }

    private void clearingStackAndMoveToHomePage(){

        Log.e(TAG,"called : clearingStackAndMoveToHomePage");

        Intent i = new Intent(mCtx, AppHomePage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mCtx.startActivity(i);
    }

    public void holdTightOnCancellation(final String date, final String lat, final String lng, final String bookingId, final String type) {
        Log.e(TAG, "called : hold up tight");

        final Dialog dialog = new Dialog(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_hold_up_tight, null);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.findViewById(R.id.tv_wait).setVisibility(View.GONE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mCtx).getWindowManager().getDefaultDisplay().getMetrics(metrics);


        float totalScreenWidth = metrics.widthPixels - 120f;
        Log.e(TAG, "totalWidth=" + totalScreenWidth);


        ImageView iv_walker = view.findViewById(R.id.image_car);
        iv_walker.animate()
                .setDuration(1000)
                .translationX(totalScreenWidth);

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                launchBookingCancelled(date, lat, lng, bookingId, type);
            }
        }, 1000);

    }

    private void launchBookingCancelled(final String date, final String lat, final String lng, final String bookingId, final String type) {
        final Dialog dialog1 = new Dialog(mCtx, android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.setContentView(R.layout.dialog_booking_cancelled);

        dialog1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialog1.getWindow().setStatusBarColor(ContextCompat.getColor(mCtx, R.color.red_dark));

        TextView track = dialog1.findViewById(R.id.btn_track);
        dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {


            }
        });
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                mCtx.startActivity(new Intent(mCtx, TrackOrder2.class).putExtra("lat", lat).putExtra("lng", lng).putExtra("booking_date", date).putExtra("booking_id", bookingId).putExtra("type", type));

            }
        });

        ImageView back = dialog1.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


}
