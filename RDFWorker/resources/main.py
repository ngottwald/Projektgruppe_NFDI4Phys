from cgitb import handler
import caosdb as db
import matplotlib
import matplotlib.pyplot as plt
import rdf_worker
import sys
import logging
import datetime
import time
import os

logger = logging.getLogger()
ct = datetime.datetime.now()

if not 'logs'in os.listdir():
            os.mkdir('logs')

formatter = logging.Formatter('[%(asctime)s - %(name)s - %(levelname)s ] - %(message)s')
log_handler = logging.FileHandler(f'logs/logs_{ct.strftime("%y%m%d_%H%M%S")}.log')
log_handler.setFormatter(formatter)
logger.addHandler(log_handler)

def my_handler(type, value, tb):
    logger.exception("Uncaught exception: {0}".format(str(value)))

# Install exception handler
sys.excepthook = my_handler


def read_from_caosdb(db):

    response = db.execute_query(f'FIND RECORD "Camera 01"')
    print(response)
    print(type(response))
    pass

def main():
    print(f'Number of arguments {len(sys.argv)}')
    logger.info("Start ...")

    if len(sys.argv) < 3:
        logger.error('Wrong number of arguments.')
        return
    mode = sys.argv[1]
    argument_1 = sys.argv[2]

    try:
        # conf = dict(db.configuration.get_config().items("Connection"))
        conf = db.configure_connection(
            url="http://mbe3.informatik.uni-siegen.de/",
            password_method="plain",
            username="testuser",
            password="NFDI4Phys",
            cacert="C:/Users/josef/workspace/Projektgruppe_NFDI4Phys/RDFWorker/resources/certificates/cert.pem"
        )
        print("##### Config:\n{}\n".format(conf))
        logger.info(f'CaosDB - Config:{conf}')

        rdf_worker1 = rdf_worker.RDFWorker(db, logger)
        rdf_worker1.test_db_connection()

        print(f'Execution mode: {mode}')
        if mode == "1" :
            logger.info(f'Upload mode')
            
            rdf_worker1.import_rdf_data(argument_1)
            rdf_worker1.export_caosdb_data_model()
        elif mode == "2":
            logger.info(f'Reading mode')
            if len(sys.argv) >= 4:
                argument_2 = sys.argv[3]
                rdf_worker1.read_record_from_caosdb_into_file(argument_1, argument_2)
            else:
                return
        
    except Exception as e:
        print('Error occours while initialization:')
        print(e)
        logger.error('Error occours while initialization:')
        logger.error(e)


if __name__ == '__main__':
    main()
