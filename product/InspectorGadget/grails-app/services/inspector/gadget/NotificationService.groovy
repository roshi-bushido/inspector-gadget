package inspector.gadget

import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.notification.Notification

class NotificationService {
    static transactional = false
    def mailService
    def grailsApplication

    def isRunning() {
        def host = mailService.mailConfig?.host;
        def port = mailService.mailConfig?.port;
        def couldConnect = false

        try {
            def s = new Socket(host, Integer.valueOf(port));

            if (s.isConnected()) {
                couldConnect = true
            }
            s.close()
        } catch (Exception e) {
            couldConnect = false
            log.warn(e.getCause())
        }
        return couldConnect
    }

    void fireErrorNotification(JobInstance instance) {
        def sendNotificationClosure = {
            def notification = new Notification(job: instance.job, jobInstance: instance)
            try {
                sendJobInstanceNotification(
                            instance?.job?.configuration?.notificationEmail,
                            grailsApplication.getConfig()?.notification?.from,
                            "Error Notification from ${instance?.job?.name}",
                            instance,
                            "/mail/errorNotification")
                notification.sentOk()
            } catch (Exception e) {
                log.error(e.getCause())
                notification.notSentDueTo(e.getMessage())
            }
        }
        sendNotificationClosure.delegate = instance.job;
        sendNotificationIfEnabled(sendNotificationClosure)
    }

    void fireWarningNotification(JobInstance instance) {
        def sendNotificationClosure = {
            def notification = new Notification(job: instance.job, jobInstance: instance)
            try {
                sendJobInstanceNotification(
                            instance?.job?.configuration?.notificationEmail,
                            grailsApplication.getConfig()?.notification?.from,
                            "Warning Notification from ${instance?.job?.name}",
                            instance,
                            "/mail/warningNotification")
                notification.sentOk()
            } catch (Exception e) {
                log.error(e.getCause())
                notification.notSentDueTo(e.getMessage())
            }
        }
        sendNotificationClosure.delegate = instance.job;
        sendNotificationIfEnabled(sendNotificationClosure)
    }

    void fireNoStartupNotification(Job instance) {
        def sendNotificationClosure = {
            def notification = new Notification(job: instance)
            try {
                sendJobNotification(
                            instance?.configuration?.notificationEmail,
                            grailsApplication.getConfig()?.notification?.from,
                            "Instance did not start for ${instance?.name}",
                            instance,
                            "/mail/noStartupNotification")
                notification.sentOk()
            } catch (Exception e) {
                log.error(e.getCause())
                notification.notSentDueTo(e.getMessage())
            }
        }
        sendNotificationClosure.delegate = instance;
        sendNotificationIfEnabled(sendNotificationClosure)
    }

    void fireJobCreationNotification(Job job) {
        def isNotificationEnabled = SystemConfiguration.findByKey("config.jobs.notification.creation.enabled").valueAsBoolean()
        if (isNotificationEnabled) {
            def notificationEmailAddress = SystemConfiguration.findByKey("config.jobs.notification.creation.email").value
            try {
                sendJobNotification(
                            notificationEmailAddress,
                            grailsApplication.getConfig()?.notification?.from,
                            "Notificacion de creacion de Nuevo Job - ${job.name}",
                            job,
                            "/mail/newJobCreated")
            } catch (Exception e) {
                log.error(e.getCause())
            }
        }
    }

    private void sendNotificationIfEnabled(Closure callable) {
        def job = callable.delegate
        def isNotificationEnabled = job?.configuration?.isNotificationEnabled
        if (isNotificationEnabled) {
            callable.call()
        } else {
            log.info("Configuration for Job (id= ${job.id}) is not enabled and no notification will be sent.")
        }
    }

    private void sendJobInstanceNotification(String toAddress, String fromAddress, String notificationSubject, JobInstance jobInstance, String template) {
        mailService.sendMail {
            to (toAddress)
            from (fromAddress)
            subject (notificationSubject)
            body (view: template, model: [instance: jobInstance])
        }
    }

    private void sendJobNotification(String toAddress, String fromAddress, String notificationSubject, Job job, String template) {
        mailService.sendMail {
            to (toAddress)
            from (fromAddress)
            subject (notificationSubject)
            body (view: template, model: [instance: job])
        }
    }
}