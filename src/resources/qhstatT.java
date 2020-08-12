/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package resources;

public class qhstatT {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected qhstatT(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(qhstatT obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        qhullJNI.delete_qhstatT(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setStats(intrealT value) {
    qhullJNI.qhstatT_stats_set(swigCPtr, this, intrealT.getCPtr(value), value);
  }

  public intrealT getStats() {
    long cPtr = qhullJNI.qhstatT_stats_get(swigCPtr, this);
    return (cPtr == 0) ? null : new intrealT(cPtr, false);
  }

  public void setId(SWIGTYPE_p_unsigned_char value) {
    qhullJNI.qhstatT_id_set(swigCPtr, this, SWIGTYPE_p_unsigned_char.getCPtr(value));
  }

  public SWIGTYPE_p_unsigned_char getId() {
    long cPtr = qhullJNI.qhstatT_id_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_char(cPtr, false);
  }

  public void setDoc(SWIGTYPE_p_p_char value) {
    qhullJNI.qhstatT_doc_set(swigCPtr, this, SWIGTYPE_p_p_char.getCPtr(value));
  }

  public SWIGTYPE_p_p_char getDoc() {
    long cPtr = qhullJNI.qhstatT_doc_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_char(cPtr, false);
  }

  public void setCount(SWIGTYPE_p_short value) {
    qhullJNI.qhstatT_count_set(swigCPtr, this, SWIGTYPE_p_short.getCPtr(value));
  }

  public SWIGTYPE_p_short getCount() {
    long cPtr = qhullJNI.qhstatT_count_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_short(cPtr, false);
  }

  public void setType(String value) {
    qhullJNI.qhstatT_type_set(swigCPtr, this, value);
  }

  public String getType() {
    return qhullJNI.qhstatT_type_get(swigCPtr, this);
  }

  public void setPrinted(String value) {
    qhullJNI.qhstatT_printed_set(swigCPtr, this, value);
  }

  public String getPrinted() {
    return qhullJNI.qhstatT_printed_get(swigCPtr, this);
  }

  public void setInit(intrealT value) {
    qhullJNI.qhstatT_init_set(swigCPtr, this, intrealT.getCPtr(value), value);
  }

  public intrealT getInit() {
    long cPtr = qhullJNI.qhstatT_init_get(swigCPtr, this);
    return (cPtr == 0) ? null : new intrealT(cPtr, false);
  }

  public void setNext(int value) {
    qhullJNI.qhstatT_next_set(swigCPtr, this, value);
  }

  public int getNext() {
    return qhullJNI.qhstatT_next_get(swigCPtr, this);
  }

  public void setPrecision(int value) {
    qhullJNI.qhstatT_precision_set(swigCPtr, this, value);
  }

  public int getPrecision() {
    return qhullJNI.qhstatT_precision_get(swigCPtr, this);
  }

  public void setVridges(int value) {
    qhullJNI.qhstatT_vridges_set(swigCPtr, this, value);
  }

  public int getVridges() {
    return qhullJNI.qhstatT_vridges_get(swigCPtr, this);
  }

  public void setTempi(int value) {
    qhullJNI.qhstatT_tempi_set(swigCPtr, this, value);
  }

  public int getTempi() {
    return qhullJNI.qhstatT_tempi_get(swigCPtr, this);
  }

  public void setTempr(double value) {
    qhullJNI.qhstatT_tempr_set(swigCPtr, this, value);
  }

  public double getTempr() {
    return qhullJNI.qhstatT_tempr_get(swigCPtr, this);
  }

  public qhstatT() {
    this(qhullJNI.new_qhstatT(), true);
  }

}